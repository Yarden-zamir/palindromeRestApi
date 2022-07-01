import config.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import logicFields.palindrome

fun main(args: Array<String>) = EngineMain.main(args)

val serializer = Json {
    encodeDefaults = true
    prettyPrint = true
}

fun Application.module() {

    install(ContentNegotiation) { json() }
    install(CallLogging)

    configureDatabase()
    configureStatusPage()
    configureRouting()

    configureLogicFields { //add any function you would like to evaluate as logic field
        add(::palindrome)
    }

    gameState(players)


    onPress("w",gmaeState){
        moveEntityForward()
        return gamesState
    }
}

