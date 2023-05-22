package admin

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
import api.ApiDog
import api.ApiVetclinic
import dog.DogAdapter
import dto.DogDtoGet
import dto.TreatmentDtoPost
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import interfaces.TreatmentInterface
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


class AdminAddClinicFragment : Fragment() {

    private lateinit var adminAddClinicName: EditText
    private lateinit var adminAddClinicCountry: EditText
    private lateinit var adminAddClinicPhone: EditText
    private lateinit var adminAddClinicRegion: EditText
    private lateinit var adminAddClinicDistrict: EditText
    private lateinit var adminAddClinicCity: EditText
    private lateinit var adminAddClinicStreet: EditText
    private lateinit var adminAddClinicHouse: EditText
    private lateinit var adminAddClinicDiscriptoin: EditText
    private lateinit var adminAddClinicServices: EditText

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private lateinit var adminAddClinic: Button
    private lateinit var adminCancelAddClinic: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_add_clinic, container, false)

        adminAddClinicName = view.findViewById(R.id.adminAddClinicName)
        adminAddClinicCountry = view.findViewById(R.id.adminAddClinicCountry)
        adminAddClinicPhone = view.findViewById(R.id.adminAddClinicPhone)
        adminAddClinicDiscriptoin = view.findViewById(R.id.adminAddClinicDisription)
        adminAddClinicRegion = view.findViewById(R.id.adminAddClinicRegion)
        adminAddClinicDistrict = view.findViewById(R.id.adminAddClinicDistrict)
        adminAddClinicCity = view.findViewById(R.id.adminAddClinicCity)
        adminAddClinicStreet = view.findViewById(R.id.adminAddClinicStreet)
        adminAddClinicHouse = view.findViewById(R.id.adminAddClinicHouse)
        adminAddClinicServices = view.findViewById(R.id.adminAddClinicServices)

        adminAddClinic = view.findViewById(R.id.adminAddClinic)
        adminCancelAddClinic = view.findViewById(R.id.adminCancelAddClinic)

        adminAddClinic.setOnClickListener() {

            addClinic(adminAddClinicPhone, adminAddClinicName, adminAddClinicCountry,
                adminAddClinicRegion, adminAddClinicDistrict, adminAddClinicCity,
                adminAddClinicStreet, adminAddClinicHouse, adminAddClinicDiscriptoin, adminAddClinicServices)


            it.findNavController().popBackStack()
        }

        adminCancelAddClinic.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        return view
    }


    private fun addClinic(phone: EditText, name: EditText, country: EditText,
                          region: EditText, district: EditText, city: EditText, street: EditText,
                          house: EditText, discription: EditText, services: EditText) {

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(VetclinicInterface::class.java)
        val dto = VetclinicDtoPost(name.text.toString(), phone.text.toString(),
            discription.text.toString(), country.text.toString(), region.text.toString(),
            district.text.toString(), city.text.toString(), street.text.toString(), house.text.toString())

        try {

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewVetclinic(dto, headers).execute()

                if (response.isSuccessful) {
                    println("clinic was added")
                    addTreatments(services)
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }

    }



    private fun addTreatments(services: EditText) {
        val api = retrofit.create(TreatmentInterface::class.java)

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = ApiVetclinic.service.getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    println("List clinics" + dataResponse)
                    if (dataResponse != null) {
                        val newClinicId = dataResponse[dataResponse.size - 1].id

                        val prepairList = parseServices(services.text.toString())

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                for (i in 0 until prepairList.size - 1 step 2) {
                                    val dto = TreatmentDtoPost(prepairList[i], prepairList[i + 1].toBigDecimal())
//                                    println("dto " + i / 2 + " " + dto)
//                                    println(newClinicId)
                                    val responseTreatment = api.saveNewTreatment(newClinicId, dto, headers).execute()
//                                    println(responseTreatment.isSuccessful)
//                                    println(responseTreatment.code())
//                                    println(responseTreatment.message())
                                }
                            } catch (ex: Exception) {
                                println("Что-то не так")
                                println(ex)
                                ex.stackTrace
                            }
                        }
                    }
                } else {
                    println("AAAAAAAAAAAAAAAA")
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("BBBBBBBBBBBBBBBBB")
            }
        })
    }
//name1,price1;name2,price2


    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

    private fun parseServices(input: String): List<String> {
        val result = mutableListOf<String>()
        val pairs = input.split(";")

        for (pair in pairs) {
            val values = pair.split(",")
            result.addAll(values)
        }

        return result
    }

}