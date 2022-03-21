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
            assertTrue(response.content?.startsWith("Message created") ?: false)
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
            assertEquals(LocalDate.now().toString(), message.datePosted)
            assertEquals(LocalDate.now().toString(), message.dateEdited)


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

    @Test
    fun `Should get list of messages that isn't empty`() = withTestApplication(Application::module) {
        val messageText = "Apple"
        withCreateMessage(messageText) { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
        }
        withGetAllMessages { response, messages ->
            assertEquals(HttpStatusCode.Found, response.status())
            assertTrue(messages.isNotEmpty())
        }
    }


    @Test
    fun `update`() {
        TODO()
    }

    @Test
    fun `delete`() {
        TODO()
    }


}


