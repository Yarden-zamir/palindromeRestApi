import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import routes.messages
import routes.root

//file responsible for the implementation of routing, but not the routes themselves

fun Application.configureRouting() {
    routing {
        root()
        messages()
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