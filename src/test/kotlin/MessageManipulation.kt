import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import org.junit.jupiter.api.Test


internal class MessageManipulation {
    @Test
    fun testRequests() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Post, "/signup"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(listOf("username" to "JetBrains", "email" to "example@jetbrains.com", "password" to "foobar", "confirmation" to "foobar").formUrlEncode())
        }) {
            assertEquals("The 'JetBrains' account is created", response.content)
        }
    }

    @Test
    fun `create`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Post, "/messages") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
            setBody(
                listOf(
                    "text" to "Pomegranate"
                ).formUrlEncode()
            )
        }) {
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