package profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import api.ApiDog

import dog.DogAdapter
import dog.DogModel
import dto.DogDtoGet
import dto.VetclinicDtoGet
import interfaces.AuthInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import medical.ClinicsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate


class ProfileFragment : Fragment() {

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var addDogButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var addEventsButton: Button
    private lateinit var exitProfileButton: Button

    private var userName:TextView? = null


    private lateinit var recyclerView: RecyclerView
    private var dogsAdapter: DogAdapter? = null

    private  var userId: Int? = null
    private  var userPassword: String? = null
    private  var name: String? = null


    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)

        getUserData(getLoginFromSharedPreferences(), getTokenFromSharedPreferences())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = ApiDog.service.getDogsOwner(getLoginFromSharedPreferences(), headers)

        call.enqueue(object : Callback<List<DogDtoGet>> {
            override fun onResponse(call: Call<List<DogDtoGet>>, response: Response<List<DogDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    println(dataResponse)
                    dogsAdapter = DogAdapter(dataResponse as MutableList<DogDtoGet>)
                    recyclerView.adapter = dogsAdapter

                } else {
                    println("Не успешно")
                }
            }

            override fun onFailure(call: Call<List<DogDtoGet>>, t: Throwable) {
                println("Ошибка")
                println(t)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        println("==================")
        println(getTokenFromSharedPreferences())
        println(getLoginFromSharedPreferences())

        if (getTokenFromSharedPreferences() == "" || getLoginFromSharedPreferences() == "") {
            startLoginFragment()
        }

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerView = view.findViewById(R.id.recyclerDogs)
        recyclerView.layoutManager = LinearLayoutManager(activity)


//        recyclerView.adapter = eventsAdapter

        addDogButton = view.findViewById(R.id.addDogButton)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        addEventsButton = view.findViewById(R.id.eventsButton)
        exitProfileButton = view.findViewById(R.id.exitButton)

        userName = view.findViewById(R.id.userName)
        getUserName(getLoginFromSharedPreferences(), getTokenFromSharedPreferences())

        addDogButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_dogAddFragment)
        }

        addEventsButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_eventsFragment)
        }

        editProfileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        exitProfileButton.setOnClickListener {
            showExitConfirmationDialog()
        }

        return view
    }

    private fun startAdminFragment() {
        findNavController().navigate(R.id.action_profileFragment_to_adminClinicsFragment)
    }



    private fun getUserData(login: String, token: String) {
        val api = retrofit.create(OwnerInterface::class.java)

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        try {
            CoroutineScope(Dispatchers.IO).launch {

                val response = api.findByLogin(login, headers).execute()

                if (response.isSuccessful) {
//                    userId = response.body()?.id
                    userPassword = response.body()?.password
                    name = response.body()?.name


                    println(response.body())

                    if (response.body()?.roles?.contains("ADMIN") == true) {
                        requireActivity().runOnUiThread {
                           startAdminFragment()
                        }
                    }
//                    if (response.body()?.roles?.contains("USER") == true &&
//                        response.body()?.roles?.contains("ADMIN") == false) {
//                        requireActivity().runOnUiThread {
//
//                        }
//                    }
                } else {
                    requireActivity().runOnUiThread {
                        startLoginFragment()
                    }
                    println("D:")
                }


            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }



    private fun getUserName(login: String, token: String) {
        val api = retrofit.create(OwnerInterface::class.java)

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.findByLogin(login, headers).execute()
                if (response.isSuccessful) {
                    userName?.text = response.body()?.name
                }

            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    private fun showExitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("Подтверждение")
        alertDialogBuilder.setMessage("Вы уверены, что хотите выйти из профиля?")

        alertDialogBuilder.setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(this.context, "Вы вышли из профиля", Toast.LENGTH_SHORT).show()
            clearSharedPreferencesToken()
            clearSharedPreferencesLogin()
//            clearSharedPreferencesId()
//            findNavController().navigate(R.id.profileFragment)
            startLoginFragment()
        }

        alertDialogBuilder.setNegativeButton("Нет") { dialogInterface: DialogInterface, i: Int ->

        }

        alertDialogBuilder.create().show()
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

    private fun startLoginFragment() {
        findNavController().navigate(R.id.loginFragment)
//        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

}