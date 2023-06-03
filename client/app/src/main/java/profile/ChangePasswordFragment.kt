package profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yandex.metrica.YandexMetrica
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class ChangePasswordFragment : Fragment() {

    private lateinit var completeChangePasswordButton: Button
    private lateinit var cancelChangePasswordButton: Button

    private lateinit var newPassword: EditText
    private lateinit var repeatNewPassword: EditText

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        completeChangePasswordButton = view.findViewById(R.id.completeChangePasswordButton)
        cancelChangePasswordButton = view.findViewById(R.id.cancelChangePasswordButton)

        newPassword = view.findViewById(R.id.newPassword)
        repeatNewPassword = view.findViewById(R.id.repeatNewPassword)

        completeChangePasswordButton.setOnClickListener {
            if (checkPasswords(newPassword, repeatNewPassword)) {
                changePassword(newPassword.text.toString())
            }

        }

        cancelChangePasswordButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun checkPasswords(new: EditText, repeat: EditText): Boolean {
        var isValid = true

        if (new.text.toString().isEmpty()) {
            new.error = "Введите новый пароль"
            isValid = false
        }

        if (repeat.text.toString().isEmpty()) {
            repeat.error = "Повторите новый пароль"
            isValid = false
        }

        if (new.text.toString() != repeat.text.toString()) {
            new.error = "Новые пароли не совпадают"
            repeat.error = "Новые пароли не совпадают"
            isValid = false
        }

        return isValid
    }

    private fun changePassword(newPass: String) {
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(OwnerInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                println("pass - " + newPass)
                val response = api.updatePassword(getLoginFromSharedPreferences(), newPass, headers).execute()
                if (response.isSuccessful) {
                    println("L:D")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        clearSharedPreferencesLogin()
                        clearSharedPreferencesToken()
                        requireActivity().runOnUiThread {
                            YandexMetrica.reportEvent("Пользователь сменил пароль")
                            findNavController().navigate(R.id.loginFragment)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(),
                            "Используйте в пароле спец символы и цифры", Toast.LENGTH_LONG).show()
                    }
                    println(response.code())
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "Что-то пошло не так, попробуйте ещё раз позже",
                Toast.LENGTH_LONG).show()
            println(ex)
        }
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

    private fun clearSharedPreferencesToken() {
        val editor = sharedPreferencesToken.edit()
        editor.clear()
        editor.apply()
    }

    private fun clearSharedPreferencesLogin() {
        val editor = sharedPreferencesLogin.edit()
        editor.clear()
        editor.apply()
    }

}