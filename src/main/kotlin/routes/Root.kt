package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.root() {
    get("/") {
        call.respondText("Hello world!")
    }
}