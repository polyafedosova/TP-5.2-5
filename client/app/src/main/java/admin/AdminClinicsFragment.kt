package admin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.ApiVetclinic
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dog.DogAdapter
import dto.DogDtoGet
import dto.VetclinicDtoGet
import medical.ClinicsAdapter
import medical.ClinicsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.tp.paws.R

class AdminClinicsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adminClinicsAdapter: AdminClinicsAdapter

    private lateinit var adminAddClinicButton: FloatingActionButton
    private lateinit var adminLogoutButton: FloatingActionButton

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = ApiVetclinic.service.getAllVetclinics()

        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
//                    println(dataResponse)
                    adminClinicsAdapter = AdminClinicsAdapter(dataResponse as MutableList<VetclinicDtoGet>)
                    recyclerView.adapter = adminClinicsAdapter

                } else {
                    println("Не успешно")
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("Ошибка")
                println(t)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_clinics, container, false)

        recyclerView = view.findViewById(R.id.recyclerClinics)
        recyclerView.layoutManager = LinearLayoutManager(activity)

//        adminClinicsAdapter = AdminClinicsAdapter(getDataClinics() as MutableList<AdminClinicsModel>)
//        recyclerView.adapter = adminClinicsAdapter

        adminAddClinicButton = view.findViewById(R.id.adminAddClinicButton)
        adminLogoutButton = view.findViewById(R.id.adminLogoutButton)

        adminAddClinicButton.setOnClickListener() {
            it.findNavController().navigate(R.id.action_adminClinicsFragment_to_adminAddClinicFragment)
        }

        adminLogoutButton.setOnClickListener() {
            showExitConfirmationDialog()
        }

        return view
    }

    private fun showExitConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("Подтверждение")
        alertDialogBuilder.setMessage("Вы уверены, что хотите выйти из профиля?")

        alertDialogBuilder.setPositiveButton("Да") { dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(this.context, "Вы вышли из профиля", Toast.LENGTH_SHORT).show()
            clearSharedPreferencesToken()
            clearSharedPreferencesLogin()

            findNavController().navigate(R.id.action_adminClinicsFragment_to_loginFragment)
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


    private fun getDataClinics(): List<AdminClinicsModel> {
        val listClinics: MutableList<AdminClinicsModel> = java.util.ArrayList()
        listClinics.add(
            AdminClinicsModel( "Лаповое", "adress1",
                "10", "service1", "phone1"))
        listClinics.add(
            AdminClinicsModel( "Крутое название", "adress2",
                "200", "service2", "phone2"))

        return listClinics
    }
}