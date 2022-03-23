import io.ktor.application.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

fun Application.configureDatabase() {
    MessagesDb.loadDb();
}

val serializer = Json {
    encodeDefaults = true
    prettyPrint = true
}

object MessagesDb {
    private var messagesList: MutableList<Message> = mutableListOf<Message>()
    fun loadDb(initialData: MutableList<Message> = mutableListOf<Message>()) {
        messagesList = initialData
    }

    fun getMessages(): List<Message> {
        return messagesList
    }

    fun getMessage(id: Int): Message? {
        return messagesList.find { it.id == id }
    }

    fun addMessage(message: Message) {
        messagesList.add(message)
    }

    fun removeMessage(id: Int): Boolean {
        return messagesList.removeIf { it.id == id }
    }

    fun asJson(): String {
        return Json { prettyPrint = true }.encodeToString(messagesList)
    }
}




@Serializable
data class Message(
    var text: String,
    val id: Int,
    val datePosted: String = LocalDateTime.now().toString(),
    var dateEdited: String = datePosted,
) {
    val logicFields: Map<String, String>
        get() = evaluateLogicFields()

    fun getLogicFieldsAsJson(): String {
        return serializer.encodeToString(logicFields)
    }

    fun asJson(): String {
        return serializer.encodeToString(this)
    }
}