import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import logicFields.isPalindrome
import kotlin.reflect.KFunction1

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) { json() }
    install(CallLogging)

    configureDatabase()
    configureStatusPage()
    configureRouting()

    configureLogicFields {
        add(::isPalindrome)
    }
}

