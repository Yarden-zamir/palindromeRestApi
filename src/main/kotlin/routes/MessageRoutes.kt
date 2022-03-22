package routes

import Message
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDateTime

//route content


fun Route.messages() {
    route("/messages") {
        createMessage()
        getMessages()
        getMessage()
        updateMessage()
        deleteMessage()
    }
}

private fun Route.createMessage() {
    post {
        val parameters = call.receiveParameters()
        val messageText = parameters["text"] ?: return@post call.respondText(
            "No text parameter for message", status = HttpStatusCode.BadRequest
        )
        val message = Message(messageText)
        MessagesDb.addMessage(message)
        call.respondText("Message created with id ${message.id}", status = HttpStatusCode.Created)
    }
}

private fun Route.getMessages() {
    get {
        if (MessagesDb.getMessages().isEmpty()) call.respondText(
            "No messages found", status = HttpStatusCode.NotFound
        )
        else call.respondText("Messages : " + MessagesDb.getMessages().toString(), status = HttpStatusCode.Found)

    }
}

private fun Route.getMessage() {
    get("{id}") {
        val idNumber = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(
            //we know that parameter id will never be null as this route happens only when it gets past, therefore the '!!'
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )

        val message = MessagesDb.getMessages().find { it.id == idNumber }?.toString() ?: return@get call.respondText(
            "No message with id $idNumber", status = HttpStatusCode.NotFound
        )
        call.respondText(message, status = HttpStatusCode.Found)
    }
}

private fun Route.updateMessage() {
    put("{id}") {
        val parameters = call.receiveParameters()
        val idNumber = call.parameters["id"]!!.toIntOrNull() ?: return@put call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.getMessages().find { it.id == idNumber } ?: return@put call.respondText(
            "No message with id $idNumber", status = HttpStatusCode.NotFound
        )
        message.dateEdited = LocalDateTime.now().toString()
        message.text = parameters["text"] ?: return@put call.respondText(
            "Text parameter is illegal or missing", status = HttpStatusCode.BadRequest
        )
        call.respondText(message.toString(), status = HttpStatusCode.OK)
    }
}

private fun Route.deleteMessage() {
    delete("{id}") {
        val idNumber = call.parameters["id"]!!.toIntOrNull() ?: return@delete call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        if (MessagesDb.removeMessage(idNumber)) call.respondText("Message with id $idNumber removed", status = HttpStatusCode.NoContent)
        else call.respondText("No message with id $idNumber", status = HttpStatusCode.NotFound)
    }
}