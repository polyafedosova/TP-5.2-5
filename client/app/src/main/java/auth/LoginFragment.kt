package auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R

class LoginFragment : Fragment() {

    private lateinit var userLogin: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var toRegisterButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        userLogin = view.findViewById(R.id.userLoginEntry)
        userPassword = view.findViewById(R.id.userPasswordEntry)

        loginButton = view.findViewById(R.id.loginButton)
        toRegisterButton = view.findViewById(R.id.toRegisterButton)

        loginButton.setOnClickListener() {
            try {
                checkInputAndAuth(userLogin, userPassword)
            } catch (ex: Exception) {}
        }

        toRegisterButton.setOnClickListener() {
//            it.findNavController().navigate(R.id.loginFragment)
//            it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view
    }

    private fun checkInputAndAuth(login: EditText, password: EditText) {
        var flag = false
        if (login.text.toString() == "" || login.text.toString() == " ") {
            flag = true
            Toast.makeText(this.requireContext(), "Invalid login", Toast.LENGTH_SHORT).show()
        }
        if (password.text.toString() == "" || password.text.toString() == " ") {
            flag = true
            Toast.makeText(this.requireContext(), "Invalid password", Toast.LENGTH_SHORT).show()
        }
        if (!flag) {
            authorization(login, password)
        }

    }
    private fun authorization(login: EditText, password: EditText) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

//        if (userexist) {
//
//        } else {
//
//        }

    }

}