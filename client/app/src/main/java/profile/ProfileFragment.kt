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
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController

import auth.LoginFragment
import dog.DogAdapter
import dog.DogModel
import dto.OwnerDtoGet
import interfaces.AuthInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    private lateinit var eventsAdapter: DogAdapter

    private  var userId: Int? = null
    private  var userLogin: String? = null
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

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        println("==================")
//        println(getTokenFromSharedPreferences())
//        println(getLoginFromSharedPreferences())

        if (getTokenFromSharedPreferences() == "" || getLoginFromSharedPreferences() == "") {
            startLoginFragment()
        }

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerView = view.findViewById(R.id.recyclerDogs)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        eventsAdapter = DogAdapter(getDataDogs() as MutableList<DogModel>)
        recyclerView.adapter = eventsAdapter

        addDogButton = view.findViewById(R.id.addDogButton)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        addEventsButton = view.findViewById(R.id.eventsButton)
        exitProfileButton = view.findViewById(R.id.exitButton)

        userName = view.findViewById(R.id.userName)
        getUserData(getLoginFromSharedPreferences())

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

    private fun getUserData(login: String) {
        val api = retrofit.create(OwnerInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.findByLogin(login).execute()
                if (response.isSuccessful) {
                    userId = response.body()?.id
                    name = response.body()?.name
                    userPassword = response.body()?.password
                    userName?.text = name
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
//        findNavController().navigate(R.id.profileFragment)
        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

    //временные костыли

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataDogs(): List<DogModel> {
        val listDogs: MutableList<DogModel> = java.util.ArrayList()
        val date: LocalDate = LocalDate.of(2015, 7,24)
        val dateString = date.year.toString()+ " " + date.month.value.toString() + " " + date.dayOfMonth.toString()

        listDogs.add(DogModel(1,"Собака 1", dateString, "Порода 1"))
        listDogs.add(DogModel(2,"Собака 2", dateString, "Порода 2"))

        return listDogs
    }

}