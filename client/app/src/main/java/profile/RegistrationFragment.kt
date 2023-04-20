package profile

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.vsu.cs.tp.paws.R


class RegistrationFragment : Fragment() {

    private lateinit var completeRegistrationButton: Button
    private lateinit var userLogin: EditText
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText
    private lateinit var userRepeatPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_registration, container, false)

        completeRegistrationButton = view.findViewById(R.id.completeRegistrationButton)

        userLogin = view.findViewById(R.id.userLogin)
        userName = view.findViewById(R.id.userName)
        userPassword = view.findViewById(R.id.userPassword)
        userRepeatPassword = view.findViewById(R.id.userRepeatPassword)

        completeRegistrationButton.setOnClickListener() {
            correctInputCheck(userLogin, userName, userPassword, userRepeatPassword)
        }

        return view
    }

    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }

    private fun correctInputCheck(login: EditText, name: EditText, password: EditText, repeatPassword: EditText) {
        if (isEmpty(login) && isEmpty(name) && isEmpty(password) && isEmpty(repeatPassword)) {
//            login.setError("шо?")
            Toast.makeText(this.requireContext(), "Нужно заполнить все поля", Toast.LENGTH_SHORT).show()
        }

        if (!password.text.equals(repeatPassword.text)) {
            Toast.makeText(this.requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()
        }

        //проверка на индивидуальный логин

    }

}