import io.ktor.http.*
import io.ktor.server.testing.*

fun TestApplicationEngine.withCreateMessage(
    messageText: String,
    action: (TestApplicationResponse, Message) -> Unit
){
    with(handleRequest(HttpMethod.Post, "/messages") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        val message = MessagesDb.getMessages().last()
        action(response, message)
    }
}

fun TestApplicationEngine.withUpdateMessage(
    messageId: Int,
    messageText: String,
    action: (TestApplicationResponse, Message?) -> Unit
) {
    with(handleRequest(HttpMethod.Put, "/messages/$messageId") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        action(response, MessagesDb.getMessages().find { it.id == messageId })
    }

}

fun TestApplicationEngine.withGetMessage(messageId: Int, action: (TestApplicationResponse, Message?) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages/$messageId")) {
        action(response, MessagesDb.getMessage(messageId))
    }

}

fun TestApplicationEngine.withGetAllMessages(action: (TestApplicationResponse, List<Message>) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages")) {
        action(response, MessagesDb.getMessages())
    }
}

fun TestApplicationEngine.withDeleteMessage(messageId: Int, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Delete, "/messages/$messageId")) {
        action(response)
    }
}