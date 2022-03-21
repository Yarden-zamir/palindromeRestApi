import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate


internal class MessageManipulation {

    @Test
    fun `Should create a message`() = withTestApplication(Application::module) {
        val messageText = "Pomegranate"
        withCreateMessage(messageText) { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertEquals("Message created", response.content)
            assertNotNull(message)
            assertNotNull(message.text)
            assertEquals(messageText, message.text)
            assertEquals(message.dateEdited, message.datePosted)
        }
    }

    @Test
    fun `Should retrieve existing message`() = withTestApplication(Application::module) {
        val messageText = "Tomato"
        val index = MessagesDb.getMessages().size
        withCreateMessage(messageText) { response, _ ->
            assertEquals(HttpStatusCode.Created, response.status())
        }
        withGetMessage(index) { response, message ->
            assertEquals(HttpStatusCode.Found, response.status())
            message ?: return@withGetMessage
            assertEquals(messageText, message.text)
            assertEquals(LocalDate.now().toString(),message.datePosted)
            assertEquals(LocalDate.now().toString(),message.dateEdited)


        }
    }

    @Test
    fun `Should get empty list of messages`() {
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