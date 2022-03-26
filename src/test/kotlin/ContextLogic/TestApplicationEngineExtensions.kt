package ContextLogic

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import model.Message
import serializer

fun TestApplicationEngine.withCreateMessage(
    messageText: String,
    action: (TestApplicationResponse, Message) -> Unit
): Message {
    with(handleRequest(HttpMethod.Post, "/messages") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        println(messageText + response.status())
        val message = getMessageFromResponse(response)!!
        action(response, message)
        return message
    }
}

fun TestApplicationEngine.withReplaceMessage(
    id: Int,
    messageText: String,
    action: (TestApplicationResponse, Message) -> Unit
): Message {
    with(handleRequest(HttpMethod.Put, "/messages/$id") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        val message = getMessageFromResponseNotNull(response)
        action(response, message)
        return message
    }
}

fun TestApplicationEngine.withUpdateMessage(
    id: Int,
    messageText: String,
    action: (TestApplicationResponse, Message?) -> Unit
): Message? {
    with(handleRequest(HttpMethod.Patch, "/messages/$id") {
        addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
        setBody(listOf("text" to messageText).formUrlEncode())
    }) {
        val message = getMessageFromResponse(response)
        action(response, message)
        return message
    }

}

fun TestApplicationEngine.withGetMessage(messageId: Int, action: (TestApplicationResponse, Message?) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages/$messageId")) {
        action(response, getMessageFromResponse(response))
    }

}

fun TestApplicationEngine.withGetAllMessages(action: (TestApplicationResponse, List<Message>) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages")) {
        action(response, getMessagesFromResponse(response))
    }
}

fun TestApplicationEngine.withDeleteMessage(messageId: Int, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Delete, "/messages/$messageId")) {
        action(response)
    }
}

fun TestApplicationEngine.withGetField(messageId: Int, fieldName: String, action: (TestApplicationResponse) -> Unit) {
    with(handleRequest(HttpMethod.Get, "/messages/$messageId/$fieldName")) {
        action(response)
    }
}

fun TestApplicationEngine.withGetLogicField(
    messageId: Int,
    fieldName: String,
    action: (TestApplicationResponse) -> Unit
) {
    with(handleRequest(HttpMethod.Get, "/messages/$messageId/logicfields/$fieldName")) {
        action(response)
    }
}

private fun getMessageFromResponse(response: TestApplicationResponse): Message? {
    return try {
        serializer.decodeFromString<Message>(response.content ?: "")
    } catch (d: Throwable) {
        null
    }
}

private fun getMessageFromResponseNotNull(response: TestApplicationResponse): Message {
   return serializer.decodeFromString<Message>(response.content ?: "")
}

private fun getMessagesFromResponse(response: TestApplicationResponse): List<Message> {
    return try {
        serializer.decodeFromString<List<Message>>(response.content!!)
    } catch (d: Throwable) {
        listOf()
    }
}