import io.ktor.application.*
import java.time.LocalDate

fun Application.configureDatabase() {
    MessagesDb.loadDb(
        initialData = mutableListOf(
            Message("0", LocalDate.now().toString()),
            Message("1", LocalDate.EPOCH.toString(), "ELO!")
        )
    );
}

object MessagesDb {
    private val messagesList: MutableList<Message> = mutableListOf<Message>()
    fun loadDb(initialData: MutableList<Message>) {
        messagesList.addAll(initialData)
    }

    suspend fun getMessages(): List<Message> {
        return messagesList
    }
}


data class Message(val id: String, val datePosted: String, val content: String = "")