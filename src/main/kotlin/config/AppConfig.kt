package config

import database.MessageTable
import database.MessagesDb
import database.mainDb
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import model.LogicFields
import model.logicFieldsList
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import routes.messages
import routes.root

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

fun Application.configureDatabase() {
    println("!!!"+getCfg("db"))
    if (System.getenv("db")== "true")
        Database.connect(mainDb())
    else {
        val embeddedTestDb = EmbeddedPostgres.builder().start().postgresDatabase
        Database.connect(embeddedTestDb)
    }
    transaction { SchemaUtils.create(MessageTable) }
}

fun configureLogicFields(logicFunction: LogicFields.() -> Unit): LogicFields {
    logicFunction.invoke(logicFieldsList)
    return logicFieldsList
}
