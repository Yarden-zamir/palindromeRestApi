import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Routing {
    @Test
    fun `root show hello world`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/").apply {
                Assertions.assertEquals(HttpStatusCode.OK, response.status())
                Assertions.assertEquals(
                    "Hello and welcome to palindrome REST api, please visit https://github.com/PandaBoy444/palindromeRestApi for more info",
                    response.content
                )
            }
        }
    }
}