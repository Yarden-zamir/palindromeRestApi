package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Message
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime


fun mainDb(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = System.getenv("JDBC_DRIVER") ?: "org.postgresql.Driver"
    config.jdbcUrl = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:postgresql:postgres?user=postgres&password=postgres"
    config.maximumPoolSize = 3
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
}

suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
    transaction {
        block()
    }
}

object MessagesDb {

    fun getMessages(): List<Message> {
        val x = transaction {
            MessageTable.selectAll().map { it.toMessage()!! }.sortedBy { it.id }
        }
        return x
    }

    fun getMessage(id: Int): Message? {
        val x = transaction {
            MessageTable.select { MessageTable.id.eq(id) }.singleOrNull()
        }
        return x.toMessage()
    }

    private fun ResultRow?.toMessage(): Message? {
        this ?: return null
        return Message(
            id = this[MessageTable.id].value,
            text = this[MessageTable.text],
            datePosted = this[MessageTable.datePosted],
            dateEdited = this[MessageTable.dateEdited],
        )
    }

    suspend fun createMessage(messageText: String): Message {
        var newMessageId: EntityID<Int>? = null
        dbQuery {
            val now = LocalDateTime.now().toString()
            newMessageId = MessageTable.insertAndGetId { table ->
                table[MessageTable.text] = messageText
                table[MessageTable.datePosted] = now
                table[MessageTable.dateEdited] = now
            }
        }
        return getMessage(newMessageId!!.value)!!

    }

    suspend fun replaceMessage(id: Int, messageText: String): Message {
        dbQuery {
            val now = LocalDateTime.now().toString()
            MessageTable.insertIgnore { table ->
                table[MessageTable.id] = EntityID(id, MessageTable)
                table[MessageTable.text] = messageText
                table[MessageTable.datePosted] = now
                table[MessageTable.dateEdited] = now
            }
        }
        return getMessage(id)!!
    }

    suspend fun updateMessage(id: Int, text: String): Message? {
        dbQuery {
            val now = LocalDateTime.now().toString()
            MessageTable.update(where = {
                MessageTable.id eq id
            }) { table ->
                table[MessageTable.text] = text
                table[MessageTable.dateEdited] = LocalDateTime.now().toString()
            }
        }
        return getMessage(id)
    }

    suspend fun doesMessageExists(id: Int): Boolean {
        getMessage(id) ?: return false
        return true
    }

    suspend fun removeMessage(id: Int) {
        dbQuery {
            MessageTable.deleteWhere {
                MessageTable.id eq id
            }
        }
    }
}




