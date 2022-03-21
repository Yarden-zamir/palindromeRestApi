import io.ktor.application.*

fun Application.configureDatabase() {
    messagesDb.loadDb();
}

object messagesDb {
    private val messagesList: MutableList<Message> = mutableListOf<Message>()
    fun loadDb(
        initialData: MutableList<Message> = mutableListOf(
            Message("0", "yesterday")
        )
    ) {

    }
}




data class Message(val id: String, val datePosted: String, val content: String = "")