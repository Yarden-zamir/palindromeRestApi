package logicFields

import model.Message
import kotlin.reflect.KFunction1

val logicFieldsList = LogicFields()
fun configureLogicFields(logicFunction: LogicFields.() -> Unit): LogicFields {
    logicFunction.invoke(logicFieldsList)
    return logicFieldsList
}

class LogicFields : ArrayList<KFunction1<Message, String>>() {
    var enabled = true
}

fun Message.evaluateLogicFields(): Map<String, String> {
    var result: MutableMap<String, String> = mutableMapOf()
    if (logicFieldsList.enabled) logicFieldsList.forEach {
        result[it.name.lowercase()] = it.invoke(this)
    }

    return result
}