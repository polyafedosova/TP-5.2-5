package splash

class SplashConfig {

    private val noLogin = "Рады знакомству!"
    private val alreadyLogin = "С возвращением!"

    fun getFirstGreetingMessage():String {
        return noLogin
    }

    fun getCommonGreetingMessage(): String {
        return alreadyLogin
    }
}