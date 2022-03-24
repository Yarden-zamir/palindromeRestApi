package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.root() {
    get("/") {
        call.respondText("Hello and welcome to palindrome REST api, please visit https://github.com/PandaBoy444/palindromeRestApi for more info")
    }
}