package ru.vsu.cs.tp.paws

class GlobalConfig {

    private val noLogin = "Рады знакомству!"
    private val alreadyLogin = "С возвращением!"

    fun getFirstGreetingMessage():String {
        return noLogin
    }

    fun getCommonGreetingMessage(): String {
        return alreadyLogin
    }
}