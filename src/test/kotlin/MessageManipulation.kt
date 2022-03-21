import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class MessageManipulation {
    private fun createMessage(messageText: String) {

    }

    fun TestApplicationEngine.withCreateMessage(messageText: String, action: (TestApplicationResponse) -> Unit) {
        with(handleRequest(HttpMethod.Post, "/messages") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf("text" to messageText).formUrlEncode())
        }) {
            action(response)
        }

    }

    @Test
    fun `Should create a message`() = withTestApplication(Application::module) {
        withCreateMessage("Porto") { response ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertEquals("Message created", response.content)

        }
    }

    @Test
    fun `retrieve single`() {
        TODO()
    }

    @Test
    fun `retrieve all when empty`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/messages").apply {
                Assertions.assertEquals(HttpStatusCode.NotFound, response.status())
                Assertions.assertEquals(
                    "No messages found",
                    response.content
                )
            }
        }
    }

//    @Test
//    fun `retrieve all after adding message`() = withTestApplication(Application::module) {
//        with(handleRequest(HttpMethod.Post, "/messages") {
//            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
//            setBody(listOf("content" to "Pomegranate").formUrlEncode())
//        }) {
//        }
//        with(handleRequest(HttpMethod.Get, "/messages")) {
////            Assertions.assertEquals(HttpStatusCode.Found, response.status())
//            Assertions.assertEquals(
//                "Messages : [Message(content=Pomegranate, datePosted=2022-03-21, dateEdited=2022-03-21)]",
//                response.content
//            )
//        }
//    }

    @Test
    fun `update`() {
        TODO()
    }

    @Test
    fun `delete`() {
        TODO()
    }


}