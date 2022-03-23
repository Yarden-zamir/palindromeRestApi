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
    routes(
        "/messages", "/v1/messages"
    ) {
        createMessage()

        getMessages()
        getMessage()
        getMessageField()
        getMessageLogicField()

        updateMessage()

        deleteMessage()

    }
}

fun Route.routes(vararg paths: String, build: Route.() -> Unit) {
    for (path in paths) {
        route(path, build)
    }
}

var idProgression = 0
private fun Route.createMessage() {
    post {
        val parameters = call.receiveParameters()
        val messageText = parameters["text"] ?: return@post call.respondText(
            "No text parameter for message", status = HttpStatusCode.BadRequest
        )
        val message = Message(messageText, idProgression++)
        MessagesDb.addMessage(message)
        call.respondText(message.asJson(), status = HttpStatusCode.Created)
    }
}

private fun Route.getMessages() {
    get {
        if (MessagesDb.getMessages().isEmpty()) call.respondText(
            "No messages found", status = HttpStatusCode.NotFound
        )
        else call.respondText(MessagesDb.asJson(), status = HttpStatusCode.Found)

    }
}

private fun Route.getMessage() {
    get("{id}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(
            //we know that parameter id will never be null as this route happens only when it gets past, therefore the '!!'
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )

        val message = MessagesDb.getMessage(id) ?: return@get call.respondText(
            "No message with id $id", status = HttpStatusCode.NotFound
        )
        call.respondText(message.asJson(), status = HttpStatusCode.Found)
    }
}

private fun Route.updateMessage() {
    put("{id}") {
        val parameters = call.receiveParameters()
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@put call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.getMessage(id) ?: return@put call.respondText(
            "No message with id $id", status = HttpStatusCode.NotFound
        )
        message.text = parameters["text"] ?: return@put call.respondText(
            "Text parameter is illegal or missing", status = HttpStatusCode.BadRequest
        )
        message.dateEdited = LocalDateTime.now().toString()
        call.respondText(message.asJson(), status = HttpStatusCode.OK)
    }
}

private fun Route.deleteMessage() {
    delete("{id}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@delete call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        if (MessagesDb.removeMessage(id)) call.respondText(
            "Message with id $id removed",
            status = HttpStatusCode.NoContent
        )
        else call.respondText("No message with id $id", status = HttpStatusCode.NotFound)
    }
}

private fun Route.getMessageField() {
    get("{id}/{field}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.getMessage(id) ?: return@get call.respondText(
            "No message with id $id", status = HttpStatusCode.NotFound
        )
        val field = call.parameters["field"]!!
        when (field.lowercase()) {
            "text" -> call.respondText(message.text, status = HttpStatusCode.Found)
            "dateposted" -> call.respondText(message.datePosted, status = HttpStatusCode.Found)
            "dateedited" -> call.respondText(message.dateEdited, status = HttpStatusCode.Found)
            "id" -> call.respondText(message.id.toString(), status = HttpStatusCode.Found)
            "logicfields", "logicfield", "fields" -> call.respondText(
                message.getLogicFieldsAsJson(),
                status = HttpStatusCode.Found
            )
            else -> call.respondText("No field with name $field", status = HttpStatusCode.NotFound)
        }
    }
}

fun Route.getMessageLogicField() {
    get("{id}/logicfields/{logicfield}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@get call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.getMessage(id) ?: return@get call.respondText(
            "No message with id $id", status = HttpStatusCode.NotFound
        )
        val logicField = call.parameters["logicfield"]?.lowercase()
        with(message.logicFields[logicField]) {
            this ?: return@get call.respondText(
                "logic field is invalid or doesn't exist",
                status = HttpStatusCode.NotFound
            )
            call.respondText(this, status = HttpStatusCode.Found)
        }
    }
}