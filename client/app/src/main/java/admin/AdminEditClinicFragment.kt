package admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import api.ApiTreatment
import api.ApiVetclinic
import dto.TreatmentDtoGet
import dto.VetclinicDtoGet
import interfaces.DogInterface
import interfaces.VetclinicInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class AdminEditClinicFragment : Fragment() {

    private lateinit var adminEditClinicName: EditText
    private lateinit var adminEditClinicCountry: EditText
    private lateinit var adminEditClinicPhone: EditText
    private lateinit var adminEditClinicRegion: EditText
    private lateinit var adminEditClinicDistrict: EditText
    private lateinit var adminEditClinicCity: EditText
    private lateinit var adminEditClinicStreet: EditText
    private lateinit var adminEditClinicHouse: EditText
    private lateinit var adminEditClinicDiscriptoin: EditText
    private lateinit var adminEditClinicServices: EditText

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var adminEditClinic: Button
    private lateinit var adminDeleteClinic: Button
    private lateinit var adminBackFromClinic: Button

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_edit_clinic, container, false)

        adminEditClinicName = view.findViewById(R.id.adminEditClinicName)
        adminEditClinicCountry = view.findViewById(R.id.adminEditClinicCountry)
        adminEditClinicPhone = view.findViewById(R.id.adminEditClinicPhone)
        adminEditClinicDiscriptoin = view.findViewById(R.id.adminEditClinicDisription)
        adminEditClinicRegion = view.findViewById(R.id.adminEditClinicRegion)
        adminEditClinicDistrict = view.findViewById(R.id.adminEditClinicDistrict)
        adminEditClinicCity = view.findViewById(R.id.adminEditClinicCity)
        adminEditClinicStreet = view.findViewById(R.id.adminEditClinicStreet)
        adminEditClinicHouse = view.findViewById(R.id.adminEditClinicHouse)
        adminEditClinicServices = view.findViewById(R.id.adminEditClinicServices)


        val idValue = requireArguments().getInt("id")
        println("id - " + idValue)
        val nameValue = requireArguments().getString("name")
        val phoneValue = requireArguments().getString("phone")
        val descriptionValue = requireArguments().getString("description")
        val countryValue = requireArguments().getString("country")
        val regionValue = requireArguments().getString("region")
        val districtValue = requireArguments().getString("district")
        val cityValue = requireArguments().getString("city")
        val streetValue = requireArguments().getString("street")
        val houseValue = requireArguments().getString("house")

        adminEditClinicName.setText(nameValue)
        adminEditClinicPhone.setText(phoneValue)
        adminEditClinicDiscriptoin.setText(descriptionValue)
        adminEditClinicCountry.setText(countryValue)
        adminEditClinicRegion.setText(regionValue)
        adminEditClinicDistrict.setText(districtValue)
        adminEditClinicCity.setText(cityValue)
        adminEditClinicStreet.setText(streetValue)
        adminEditClinicHouse.setText(houseValue)

        getTrearments(idValue)

        adminDeleteClinic = view.findViewById(R.id.adminDeleteClinic)
        adminBackFromClinic = view.findViewById(R.id.adminBackFromClinic)

        adminDeleteClinic.setOnClickListener {
            deleteClinic(idValue)
            it.findNavController().navigate(R.id.adminClinicsFragment)
        }

        adminBackFromClinic.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun deleteClinic(idValue: Int) {

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(VetclinicInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.deleteVetclinic(idValue, headers).execute()
                if (response.isSuccessful) {
                    println("L:D")

                } else {
                    println(response.code())

                    println("D:L")
                    println(response.message())
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }

    private fun getTrearments(id: Int) {
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = ApiTreatment.service.getVetclinicTreatments(id, headers)
        call.enqueue(object : Callback<List<TreatmentDtoGet>> {
            override fun onResponse(call: Call<List<TreatmentDtoGet>>, response: Response<List<TreatmentDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    println("List treatments - " + dataResponse)
                } else {
                    println(response.code())
                    println(response.message())
                }
            }

            override fun onFailure(call: Call<List<TreatmentDtoGet>>, t: Throwable) {
                println("BBBBBBBBBBBBBBBBB")
            }
        })
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

}