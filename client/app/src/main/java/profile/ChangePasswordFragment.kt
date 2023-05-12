package profile

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class ChangePasswordFragment : Fragment() {

    private lateinit var completeChangePasswordButton: Button
    private lateinit var cancelChangePasswordButton: Button

    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText
    private lateinit var repeatNewPassword: EditText

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        completeChangePasswordButton = view.findViewById(R.id.completeChangePasswordButton)
        cancelChangePasswordButton = view.findViewById(R.id.cancelChangePasswordButton)

        oldPassword = view.findViewById(R.id.oldPassword)
        newPassword = view.findViewById(R.id.newPassword)
        repeatNewPassword = view.findViewById(R.id.repeatNewPassword)

        completeChangePasswordButton.setOnClickListener {
            if (checkPasswords(oldPassword, newPassword, repeatNewPassword)) {

            }

        }

        cancelChangePasswordButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun checkPasswords(old: EditText, new: EditText, repeat: EditText): Boolean {
        var isValid = true

        if (old.text.toString().isEmpty()) {
            old.error = "Введите старый пароль"
            isValid = false
        }

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

        //проверка старого пароля на правильность


        return isValid
    }


}