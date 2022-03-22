package logicFields

import Message

fun isPalindrome(message: Message): String {
    return (message.text.reversed() == message.text).toString()
}