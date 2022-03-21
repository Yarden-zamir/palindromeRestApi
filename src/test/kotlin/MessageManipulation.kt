import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import kotlin.test.*
import org.junit.jupiter.api.Assertions.*

internal class MessageManipulation {


    @Test
    fun `create`() {
        TODO()
    }

    @Test
    fun `retrieve single`() {
        TODO()
    }

    @Test
    fun `retrieve all`() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/messages").apply {
                Assertions.assertEquals(HttpStatusCode.OK, response.status())
                Assertions.assertEquals(
                    "[Message(id=0, datePosted=2022-03-21, content=), Message(id=1, datePosted=1970-01-01, content=ELO!)]",
                    response.content
                )
            }
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
