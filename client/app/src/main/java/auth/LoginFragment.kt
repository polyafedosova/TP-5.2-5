package auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dto.JwtGet
import dto.JwtPost
import dto.OwnerDtoGet
import interfaces.AuthInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R

class LoginFragment : Fragment() {

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var userLogin: EditText
    private lateinit var userPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var toRegisterButton: Button
    private var userInfo: OwnerDtoGet? = null

    var isSuccess = 0

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        userLogin = view.findViewById(R.id.userLoginEntry)
        userPassword = view.findViewById(R.id.userPasswordEntry)

        loginButton = view.findViewById(R.id.loginButton)
        toRegisterButton = view.findViewById(R.id.toRegisterButton)

        loginButton.setOnClickListener() {
            if (checkInput(userLogin, userPassword)){
                authorization(userLogin, userPassword)
                when (isSuccess) {
                    1 -> { Toast.makeText(requireContext(), "Неверный логин", Toast.LENGTH_SHORT).show() }
                    2 -> { Toast.makeText(requireContext(), "Неверный пароль", Toast.LENGTH_SHORT).show() }
                }

            }

        }

        toRegisterButton.setOnClickListener() {
//            it.findNavController().navigate(R.id.loginFragment)
            it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return view
    }


    private fun saveTokenToSharedPreferences(value: String) {
        val editor = sharedPreferencesToken.edit()
        editor.putString("token", value)
        editor.apply()
    }

    private fun saveLoginToSharedPreferences(value: String) {
        val editor = sharedPreferencesLogin.edit()
        editor.putString("login", value)
        editor.apply()
    }

    private fun startProfileFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
    }

    private fun startAdminFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_adminClinicsFragment)
    }

    private fun checkInput(login: EditText, password: EditText): Boolean {
        var isValid = true

        if (login.text.toString().isEmpty()) {
            login.error = "Введите логин"
            isValid = false
        }
        if (password.text.toString().isEmpty()) {
            password.error = "Введите пароль"
            isValid = false
        }

        return isValid
    }


    private fun authorization(login: EditText, password: EditText) {
        val api = retrofit.create(AuthInterface::class.java)
        val dto = JwtPost(login.text.toString(), password.text.toString())
        var data: JwtGet?

        val apiUser = retrofit.create(OwnerInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.login(dto).execute()
                if (response.isSuccessful) {
                    data = response.body()
                    data?.let { saveTokenToSharedPreferences(it.accessToken) }
                    saveLoginToSharedPreferences(login.text.toString())
                    println("L:D")

                    userInfo = apiUser.findByLogin(login.text.toString()).execute().body()

                    if (userInfo?.roles?.contains("ADMIN") == true) {
                        requireActivity().runOnUiThread {
                            startAdminFragment()
                        }
                    }
                    if (userInfo?.roles?.contains("USER") == true &&
                        userInfo?.roles?.contains("ADMIN") == false) {
                        requireActivity().runOnUiThread {
                            startProfileFragment()
//                            val bundle = Bundle()
//
//                            userInfo?.id?.let { bundle.putInt("id", it) }
//                            userInfo?.username?.let { bundle.putString("login", it) }
//                            userInfo?.password?.let { bundle.putString("password", it) }
//                            userInfo?.name?.let { bundle.putString("name", it) }

                        }
                    }


                    isSuccess = 0
                }else{
                    println(response.code())
                    if (response.code() == 403) {
                        isSuccess = 1
                    }
                    if (response.code() == 500) {
                        isSuccess = 2
                    }
                    println("D:L")
                    println(response.message())
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }

    }

}