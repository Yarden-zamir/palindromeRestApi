import io.ktor.http.*
import io.ktor.server.testing.*

fun TestApplicationEngine.withCreateMessage(
    messageText: String,
    action: (TestApplicationResponse, Message) -> Unit
) {
    with(handleRequest(HttpMethod.Post, "/messages") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        action(response, Message(messageText))
    }

}

fun TestApplicationEngine.withGetMessage(messageIndex: Int, action: (TestApplicationResponse, Message?) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages/$messageIndex")) {
        action(response, MessagesDb.getMessage(messageIndex))
    }

}

fun TestApplicationEngine.withGetAllMessages(action: (TestApplicationResponse, List<Message>) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages")) {
        action(response, MessagesDb.getMessages())
    }
}