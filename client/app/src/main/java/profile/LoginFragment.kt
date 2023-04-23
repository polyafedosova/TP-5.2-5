package profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
            if (userLogin.text.toString() == "admin") {
                it.findNavController().navigate(R.id.adminClinicsFragment)
            }

        }

        toRegisterButton.setOnClickListener() {
            it.findNavController().navigate(R.id.loginFragment)
            it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view
    }

    private fun checkCorrectLogin(login: EditText, password: EditText) {

    }

}