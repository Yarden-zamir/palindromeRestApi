import io.ktor.application.*
import kotlinx.serialization.Serializable
import java.time.LocalDate

fun Application.configureDatabase() {
    MessagesDb.loadDb();
}

object MessagesDb {
    private val messagesList: MutableList<Message> = mutableListOf<Message>()
    fun loadDb(initialData: MutableList<Message> = mutableListOf<Message>()) {
        messagesList.addAll(initialData)
    }

    suspend fun getMessages(): List<Message> {
        return messagesList
    }

    suspend fun getMessage(id: Int): Message? {
        return messagesList.getOrNull(id)
    }

    suspend fun addMessage(message: Message) {
        messagesList.add(message)
    }
}

@Serializable
data class Message(
    val text: String,
    val datePosted: String = LocalDate.now().toString(),
    val dateEdited: String = datePosted
)