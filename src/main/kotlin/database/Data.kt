package database

import io.ktor.application.*
import model.Message

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




