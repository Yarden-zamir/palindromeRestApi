import io.ktor.application.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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

    fun removeMessage(id: Int): Boolean {
        return messagesList.removeIf { it.id == id }
    }
}

var idProgression = 0

@Serializable
data class Message(
    var text: String,
    val datePosted: String = LocalDateTime.now().toString(),
    var dateEdited: String = datePosted
) {
    val id = idProgression++;
    val logicFields: Map<String, String>
        get() = evaluateLogicFields()

    override fun toString(): String {
        return "Message(text=$text, datePosted=$datePosted, dateEdited=$dateEdited,id=$id, logicFields=$logicFields)"
    }
}

fun Message.evaluateLogicFields(): Map<String, String> {
    var result: MutableMap<String, String> = mutableMapOf()
    if (logicFieldsList.enabled) logicFieldsList.forEach {
        result[it.name] = it.invoke(this)

    }

    return result
}
