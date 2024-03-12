fun main() {
    while (flag) {
        val command: Command = readCommand()
        if (command.isValid()) {
            when (command) {
                is Exit -> flag = false
                is Help -> println("Инструкция...")
                is AddPhone -> {
                    person.name = command.message[1]
                    person.phone = command.message[3]
                }

                is AddEmail -> {
                    person.name = command.message[1]
                    person.email = command.message[3]
                }

                is Show -> show(person)
            }
        } else {
            println("Ошибка ввода!!!")
        }
    }
}

var flag: Boolean = true
var person = Person()

fun show(person: Person) {
    if (person.name == "") println("Not initialized")
    else println(person)
}

fun readCommand(): Command {
    val message = readln().split(" ")
    return when (message[0]) {
        "exit" -> Exit(message)
        "help" -> Help(message)
        "show" -> Show(message)
        "add" -> {
            when (message[2]) {
                "phone" -> AddPhone(message)
                "email" -> AddEmail(message)
                else -> Help(message)
            }
        }

        else -> Help(message)
    }
}

sealed interface Command {
    fun isValid(): Boolean
}

class Exit(private val message: List<String>) : Command {
    override fun isValid(): Boolean {
        return message.size == 1 && message[0] == "exit"
    }
}

class Help(private val message: List<String>) : Command {
    override fun isValid(): Boolean {
        return message.size == 1 && message[0] == "help"
    }
}

class Show(private val message: List<String>) : Command {
    override fun isValid(): Boolean {
        return message.size == 1 && message[0] == "show"
    }
}

class AddPhone(val message: List<String>) : Command {
    override fun isValid(): Boolean {
        return message.size == 4
                && message[0] == "add"
                && message[2] == "phone"
                && message[3].matches(Regex("[0-9]+"))
    }
}

class AddEmail(val message: List<String>) : Command {
    override fun isValid(): Boolean {
        return message.size == 4
                && message[0] == "add"
                && message[2] == "email"
                && message[3].matches(Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}"))
    }
}

data class Person(
    var name: String = "",
    var phone: String = "",
    var email: String = "",
)
