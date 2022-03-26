import ContextLogic.withCreateMessage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class LogicFieldsTests {

    @Test
    fun `Logic field isPalindrome exists`(): Unit = withTestApplication(Application::module) {
        withCreateMessage("rotator") { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertNotNull(message.logicFields["palindrome"])
        }
    }

    @Test
    fun `Shows that message is palindrome`(): Unit = withTestApplication(Application::module) {
        withCreateMessage("rotator") { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertNotNull(message.logicFields["palindrome"])
            assertEquals(message.logicFields["palindrome"], "true")
        }
    }

    @Test
    fun `Shows that message is not palindrome`(): Unit = withTestApplication(Application::module) {
        withCreateMessage("squash") { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertNotNull(message.logicFields["palindrome"])
            assertEquals(message.logicFields["palindrome"], "false")
        }
    }
}