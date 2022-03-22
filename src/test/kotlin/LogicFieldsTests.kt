import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class LogicFieldsTests {
    @Test
    fun `Shows that message is palindrome`() = withTestApplication(Application::module) {
        withCreateMessage("rotator") { response, message ->
            assertEquals(HttpStatusCode.Created, response.status())
            assertNotNull(message.logicFields["isPalindrome"])
            assertEquals(message.logicFields["isPalindrome"],"true")
        }
    }

    @Test
    fun `Shows that message is not palindrome`() {
        TODO()
    }
}