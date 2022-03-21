import io.ktor.application.*
import kotlinx.serialization.Serializable
import java.time.LocalDate

fun Application.configureDatabase() {
    MessagesDb.loadDb();
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
}

var idProgression = 0

@Serializable
data class Message(
    val text: String,
    val datePosted: String = LocalDate.now().toString(),
    val dateEdited: String = datePosted
) {
    val id = idProgression++;
}