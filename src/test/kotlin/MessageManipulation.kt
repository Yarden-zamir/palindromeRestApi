import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
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
        var id = -1
        withCreateMessage(messageText) { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            id = message.id
        }
        withGetMessage(id) { response, message ->
            assertEquals(HttpStatusCode.Found, response.status())
            message ?: return@withGetMessage
            assertEquals(messageText, message.text)
            assertEquals(LocalDate.now().toString(), message.datePosted)
            assertEquals(LocalDate.now().toString(), message.dateEdited)
        }
    }

    @Test
    fun `Should get empty list of messages`() = withTestApplication(Application::module) {
        withGetAllMessages { response, messages ->
            assertEquals(HttpStatusCode.NotFound, response.status())
            assertTrue(messages.isEmpty())
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
    fun `Should update`() {

    }

    @Test
    fun `delete`() {
        TODO()
    }


}


