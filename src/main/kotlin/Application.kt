import database.configureDatabase
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import logicFields.configureLogicFields
import logicFields.palindrome

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) { json() }
    install(CallLogging)

    configureDatabase()
    configureStatusPage()
    configureRouting()

    configureLogicFields { //add any function you would like to evaluate as logic field
        add(::palindrome)
    }
}

