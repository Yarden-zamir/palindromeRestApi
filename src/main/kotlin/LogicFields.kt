val logicFieldsList = LogicFields()
fun configureLogicFields(function: LogicFields.() -> Unit): LogicFields {
    function.invoke(logicFieldsList)
    return logicFieldsList
}
class LogicFields : ArrayList<(Message) -> String>() {
    var enabled = true
}