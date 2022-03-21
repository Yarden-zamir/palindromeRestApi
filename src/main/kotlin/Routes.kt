import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

//route content

fun Route.root() {
    get("/") {
        call.respondText("Hello world!")
    }
}

fun Route.messages() {
    route("/messages") {
        get {
            //permission verification if applicable
            call.respondText { MessagesDb.getMessages().toString() }
        }
        get ("{id}"){

        }
        //maybe make it able to fetch by content too? ie get({content})


    }

}