package routes

import database.MessagesDb
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import model.asJson

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

        replaceMessage()
        updateMessage()

        deleteMessage()

    }
}

fun Route.routes(vararg paths: String, build: Route.() -> Unit) {
    for (path in paths) route(path, build)
}

var idProgression = 0
private fun Route.createMessage() {
    post {
        val parameters = call.receiveParameters()
        val messageText = parameters["text"] ?: return@post call.respondText(
            "No text parameter for message", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.createMessage(messageText)
        call.respondText(message.asJson(), status = HttpStatusCode.Created)
    }
}

private fun Route.getMessages() {
    get {
        val messages = MessagesDb.getMessages()
        call.respondText(messages.asJson(), status = HttpStatusCode.OK)
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
        call.respondText(message.asJson(), status = HttpStatusCode.OK)
    }
}

private fun Route.updateMessage() {
    patch("{id}") {
        val parameters = call.receiveParameters()
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@patch call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val text = parameters["text"] ?: return@patch call.respondText(
            "Text parameter is illegal or missing", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.updateMessage(id, text)
        if (message != null) {
            call.respondText(message.asJson(), status = HttpStatusCode.OK)
        }
    }
}

private fun Route.replaceMessage() {
    put("{id}") {
        val parameters = call.receiveParameters()
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@put call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        val text = parameters["text"] ?: return@put call.respondText(
            "Text parameter is illegal or missing", status = HttpStatusCode.BadRequest
        )
        val message = MessagesDb.replaceMessage(id,text)
        call.respondText(message.asJson(), status = HttpStatusCode.OK)
    }
}

private fun Route.deleteMessage() {
    delete("{id}") {
        val id = call.parameters["id"]!!.toIntOrNull() ?: return@delete call.respondText(
            "Illegal id, use numerical ids", status = HttpStatusCode.BadRequest
        )
        if (MessagesDb.doesMessageExists(id)) {
            MessagesDb.removeMessage(id)
            call.respondText("Message with id $id removed", status = HttpStatusCode.NoContent)
        } else
            call.respondText("No message with id $id", status = HttpStatusCode.NotFound)

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
            "text" -> call.respondText(message.text, status = HttpStatusCode.OK)
            "dateposted" -> call.respondText(message.datePosted, status = HttpStatusCode.OK)
            "dateedited" -> call.respondText(message.dateEdited, status = HttpStatusCode.OK)
            "id" -> call.respondText(message.id.toString(), status = HttpStatusCode.OK)
            "logicfields", "logicfield", "fields" -> call.respondText(
                message.getLogicFieldsAsJson(),
                status = HttpStatusCode.OK
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
            call.respondText(this, status = HttpStatusCode.OK)
        }
    }
}