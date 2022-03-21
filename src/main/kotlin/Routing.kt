import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    install(ContentNegotiation){}
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}