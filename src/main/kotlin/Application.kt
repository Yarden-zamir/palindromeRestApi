import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureRouting()
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}