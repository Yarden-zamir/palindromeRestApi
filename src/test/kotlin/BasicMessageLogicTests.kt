import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class BasicMessageLogicTests {
    @Test
    fun `Create a message`(): Unit = withTestApplication(Application::module) {
        val messageText = "Pomegranate"
        withCreateMessage(messageText) { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertNotNull(message)
            assertNotNull(message.text)
            assertEquals(messageText, message.text)
            assertEquals(message.dateEdited, message.datePosted)
        }
    }

    @Test
    fun `Retrieve existing message`() = withTestApplication(Application::module) {
        val messageText = "Tomato"
        var id: Int? = null
        withCreateMessage(messageText) { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            id = message.id
        }
        withGetMessage(id!!) { response, message ->
            assertEquals(HttpStatusCode.Found, response.status())
            message ?: return@withGetMessage
            assertEquals(messageText, message.text)
        }
    }

    @Test
    fun `Get empty list of messages`() = withTestApplication(Application::module) {
        withGetAllMessages { response, messages ->
            assertEquals(HttpStatusCode.NotFound, response.status())
            assertTrue(messages.isEmpty())
        }
    }

    @Test
    fun `Get list of messages that isn't empty`() = withTestApplication(Application::module) {
        val messageText = "Apple"
        withCreateMessage(messageText) { response, _ ->
            assertEquals(HttpStatusCode.Created, response.status())
        }
        Thread.sleep(100)
        withGetAllMessages { response, messages ->
            assertEquals(HttpStatusCode.Found, response.status())
            println(messages)
            assertTrue(messages.isNotEmpty())
        }
    }


    @Test
    fun `Update`() = withTestApplication(Application::module) {
        val messageText = "Carrot"
        val originalMessage =
            withCreateMessage(messageText) { response, _ ->
                assertEquals(HttpStatusCode.Created, response.status())
            }
        val updatedMessage =
            withUpdateMessage(originalMessage.id, "Golden Carrot") { response, _ ->
                assertEquals(HttpStatusCode.OK, response.status())
            }
        assertEquals(originalMessage.id, updatedMessage!!.id)
        assert(updatedMessage.dateEdited > updatedMessage.datePosted)
    }

    @Test
    fun `Delete`() = withTestApplication(Application::module) {
        val messageText = "Carrot"

        val message = withCreateMessage(messageText) { response, _ ->
            assertEquals(HttpStatusCode.Created, response.status())
        }

        withDeleteMessage(message.id) {
            assertEquals(HttpStatusCode.NoContent, it.status())
        }

        withGetMessage(message.id) { response, deletedMessage ->
            assertEquals(HttpStatusCode.NotFound, response.status())
            assertNull(deletedMessage)
        }
    }

    @Test
    fun `Not update none existing message`(): Unit = withTestApplication {
        withUpdateMessage(-1, "Nurdle") { response, _ ->
            assertEquals(HttpStatusCode.NotFound, response.status())
        }
    }

    @Test
    fun `Not delete none existing message`(): Unit = withTestApplication {
        withDeleteMessage(-1) {
            assertEquals(HttpStatusCode.NotFound, it.status())
        }
    }

    @Test
    fun `Get field from message`(): Unit = withTestApplication {
        val message = withCreateMessage("Potato"){ response, _ ->
            assertEquals(HttpStatusCode.Created, response.status())
        }

        TODO()
    }

    @Test
    fun `Fail to get field that doesn't exist from message`(): Unit {
        TODO()
    }

    @Test
    fun `Get logic field from message`(): Unit {
        TODO()
    }

    @Test
    fun `Fail to get logic field that doesn't exist from message`(): Unit {
        TODO()
    }

}


