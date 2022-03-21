import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
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

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error \n\n${cause.message}")
            throw cause
        }

    }
}