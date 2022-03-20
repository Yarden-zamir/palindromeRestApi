import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import org.junit.jupiter.api.Assertions.*

internal class ApplicationKtTest {

    @Test
    fun `test root`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello, world!", response.content)
            }
        }
    }
}