import kotlin.reflect.KFunction1

val logicFieldsList = LogicFields()
fun configureLogicFields(logicFunction: LogicFields.() -> Unit): LogicFields {
    logicFunction.invoke(logicFieldsList)
    return logicFieldsList
}

class LogicFields : ArrayList<KFunction1<Message, String>>() {
    var enabled = true
}