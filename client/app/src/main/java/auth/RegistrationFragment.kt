package auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dto.OwnerDtoPost
import interfaces.OwnerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class RegistrationFragment : Fragment() {

    private lateinit var completeRegistrationButton: Button
    private lateinit var cancelRegistrationButton: Button

    private lateinit var userLogin: EditText
    private lateinit var userName: EditText
    private lateinit var userPassword: EditText
    private lateinit var userRepeatPassword: EditText

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_registration, container, false)

        completeRegistrationButton = view.findViewById(R.id.completeRegistrationButton)
        cancelRegistrationButton = view.findViewById(R.id.cancelRegistrationButton)

        userLogin = view.findViewById(R.id.userLogin)
        userName = view.findViewById(R.id.userName)
        userPassword = view.findViewById(R.id.userPassword)
        userRepeatPassword = view.findViewById(R.id.userRepeatPassword)

        completeRegistrationButton.setOnClickListener() {
            if (validateFields(userLogin, userName, userPassword, userRepeatPassword)) {
                try {
                    if (registration(userLogin, userName, userPassword)){
                        it.findNavController().popBackStack()
                    }
                } catch (e: Exception) { e.stackTrace }

            }
        }

        cancelRegistrationButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }
//  login2
//
//    Test1@*!
    private fun registration(login: EditText, name: EditText, password: EditText): Boolean {
        val api = retrofit.create(OwnerApi::class.java)
        val dto = OwnerDtoPost(login.text.toString(), password.text.toString(), name.text.toString())
        var isSuccess = false
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewOwner(dto).execute()
                if (response.isSuccessful) {
                    println(":D")
                    isSuccess = true
                }else{
                    println("D:")
                    println(response.message())
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
        if (isSuccess){
            Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
        }

        return isSuccess
    }

    private fun validateFields(login: EditText, name: EditText, password: EditText, repeatPassword: EditText): Boolean {
        var isValid = true

        if (login.text.toString().isEmpty()) {
            login.error = "Введите логин"
            isValid = false
        }

        if (name.text.toString().isEmpty()) {
            name.error = "Введите имя"
            isValid = false
        }

        if (password.text.toString().isEmpty()) {
            password.error = "Введите пароль"
            isValid = false
        }

        if (repeatPassword.text.toString().isEmpty()) {
            repeatPassword.error = "Повторите пароль"
            isValid = false
        }

        if (password.text.toString() != repeatPassword.text.toString()) {
            password.error = "Пароли не совпадают"
            repeatPassword.error = "Пароли не совпадают"
            isValid = false
        }



        return isValid
    }


}