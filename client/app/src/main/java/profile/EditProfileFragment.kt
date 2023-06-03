package profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
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
import dto.OwnerDtoPost
import interfaces.DogInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class EditProfileFragment : Fragment() {

    private lateinit var completeProfileRenameButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var cancelProfileRenameButton: Button

    private lateinit var newLogin: EditText
    private lateinit var newName: EditText

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences
    private lateinit var sharedPreferencesPass: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesPass = requireActivity().getSharedPreferences("userPass", Context.MODE_PRIVATE)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        completeProfileRenameButton = view.findViewById(R.id.completeProfileRenameButton)
        changePasswordButton = view.findViewById(R.id.changePasswordButton)
        cancelProfileRenameButton = view.findViewById(R.id.cancelProfileRenameButton)

        newLogin = view.findViewById(R.id.newLogin)
        newName = view.findViewById(R.id.newName)

        completeProfileRenameButton.setOnClickListener {
            validateFieldsAndUpdate(newLogin, newName)
        }

        changePasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editProfileFragment_to_changePasswordFragment)
        }

        cancelProfileRenameButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun validateFieldsAndUpdate(login: EditText, name: EditText) {
        var isValid = true
        if (login.text.toString().isEmpty() ) {
            login.error = "Заполните поле"
            isValid = false
        }
        if (name.text.toString().isEmpty()) {
            name.error = "Заполните поле"
            isValid = false
        }

        if (isValid) {
            val token = getTokenFromSharedPreferences()
            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"

            val api = retrofit.create(OwnerInterface::class.java)

            val dto = OwnerDtoPost(login.text.toString(), getPassFromSharedPreferences(), name.text.toString(), false)

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = api.updateOwner(getLoginFromSharedPreferences(), dto, headers).execute()
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                            clearSharedPreferencesLogin()
                            clearSharedPreferencesToken()
                            requireActivity().runOnUiThread {
                                YandexMetrica.reportEvent("Пользователь отредактировал профиль")
                                findNavController().navigate(R.id.action_editProfileFragment_to_loginFragment)
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            when (response.code()) {
                                409 -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Такой логин уже используется", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                        println(response.code())
                    }
                }
            } catch (ex: Exception) {
                println(ex)
            }

        }

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
    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

    private fun getPassFromSharedPreferences(): String {
        return sharedPreferencesPass.getString("pass", "") ?: ""
    }


}