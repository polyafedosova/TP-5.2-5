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
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import api.Api
import com.yandex.metrica.YandexMetrica
import dto.TreatmentDtoGet
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


class AdminEditClinicFragment : Fragment() {

    private lateinit var adminEditClinicName: EditText
    private lateinit var adminEditClinicAddress: EditText
    private lateinit var adminEditClinicPhone: EditText
    private lateinit var adminEditClinicDiscriptoin: EditText
    private lateinit var adminEditClinicServices: EditText

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var adminEditClinic: Button
    private lateinit var adminDeleteClinic: Button
    private lateinit var adminBackFromClinic: Button

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_edit_clinic, container, false)

        adminEditClinicName = view.findViewById(R.id.adminEditClinicName)
        adminEditClinicAddress = view.findViewById(R.id.adminEditClinicAddress)
        adminEditClinicPhone = view.findViewById(R.id.adminEditClinicPhone)
        adminEditClinicDiscriptoin = view.findViewById(R.id.adminEditClinicDisription)

        adminEditClinicServices = view.findViewById(R.id.adminEditClinicServices)
        adminEditClinic = view.findViewById(R.id.adminEditClinic)


        val idValue = requireArguments().getInt("id")
        val nameValue = requireArguments().getString("name")
        val phoneValue = requireArguments().getString("phone")
        val descriptionValue = requireArguments().getString("description")
        val districtValue = requireArguments().getString("district")
        val cityValue = requireArguments().getString("city")
        val streetValue = requireArguments().getString("street")
        val houseValue = requireArguments().getString("house")

        val address = "$districtValue,$cityValue,$streetValue,$houseValue"
        adminEditClinicName.setText(nameValue)
        adminEditClinicPhone.setText(phoneValue)
        adminEditClinicDiscriptoin.setText(descriptionValue)
        adminEditClinicAddress.setText(address)

        getTrearments(idValue)

        adminDeleteClinic = view.findViewById(R.id.adminDeleteClinic)
        adminBackFromClinic = view.findViewById(R.id.adminBackFromClinic)

        adminEditClinic.setOnClickListener {
            updateClinic(adminEditClinicPhone, adminEditClinicName, adminEditClinicAddress,
                adminEditClinicDiscriptoin, adminEditClinicServices)

        }

        adminDeleteClinic.setOnClickListener {
            deleteClinic(idValue)
            it.findNavController().navigate(R.id.adminClinicsFragment)
        }

        adminBackFromClinic.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun updateClinic(phone: EditText, name: EditText, address: EditText, discription: EditText, services: EditText) {
        val idValue = requireArguments().getInt("id")
        deleteClinic(idValue)
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val resultList: List<String> = address.text.toString().split(",")
        val api = retrofit.create(VetclinicInterface::class.java)
        if (resultList.size == 4) {
            val dto = VetclinicDtoPost(name.text.toString(), phone.text.toString(),
                discription.text.toString(), "заглушка", resultList[0],
                 resultList[1], resultList[2], resultList[3])
            try {

                CoroutineScope(Dispatchers.IO).launch {
                    val response = api.saveNewVetclinic(dto, headers).execute()

                    if (response.isSuccessful) {
                        addTreatments(services)
                    }
                }
            } catch (ex: Exception) {
                ex.stackTrace
            }
        }else {
            Toast.makeText(this.requireContext(), "Ошибка в заполнении адреса", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addTreatments(services: EditText) {
        val api = retrofit.create(TreatmentInterface::class.java)

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = Api.getApiVetclinic().getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse != null) {
                        val newClinicId = dataResponse[dataResponse.size - 1].id

                        val prepairList = parseServices(services.text.toString())

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                for (i in 0 until prepairList.size - 1 step 2) {
                                    val dto = TreatmentDtoPost(prepairList[i], prepairList[i + 1].toBigDecimal())
                                    val responseTreatment = api.saveNewTreatment(newClinicId, dto, headers).execute()

                                }
                                requireActivity().runOnUiThread {
                                    Toast.makeText(requireContext(), "Клиника обновлена", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.adminClinicsFragment)
                                }
                            } catch (ex: Exception) {
                                requireActivity().runOnUiThread {
                                    Toast.makeText(requireContext(), "Проверь поля на верность заполнения", Toast.LENGTH_SHORT).show()
                                }
                                println("Что-то не так")
                                println(ex)
                                ex.stackTrace
                            }
                        }
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Проверь поля на верность заполнения", Toast.LENGTH_SHORT).show()
                    }
                    println("response not successful")
                    println(response.code().toString() + " " + response.message())
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {

            }
        })
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

    private fun deleteClinic(idValue: Int) {
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(VetclinicInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.deleteVetclinic(idValue, headers).execute()
                if (response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        YandexMetrica.reportEvent("Клиника удалена")
                    }
                } else {
                    Toast.makeText(requireContext(), "Что-то не так, попробуйте ещё раз", Toast.LENGTH_SHORT).show()
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

        val call = Api.getApiTreatment().getVetclinicTreatments(id)
        call.enqueue(object : Callback<List<TreatmentDtoGet>> {
            override fun onResponse(call: Call<List<TreatmentDtoGet>>, response: Response<List<TreatmentDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    var servicesString = ""
                    if (dataResponse != null) {
                        for (i in 0..dataResponse.size - 1)  {
                            if (i != dataResponse.size - 1) {
                                servicesString += dataResponse[i].name + "," + dataResponse[i].price + ";"
                            }else{
                                servicesString += dataResponse[i].name + "," + dataResponse[i].price
                            }

                        }
                        adminEditClinicServices.setText(servicesString)
                    }
                } else {
                    println(response.code())
                    println(response.message())
                }
            }

            override fun onFailure(call: Call<List<TreatmentDtoGet>>, t: Throwable) {
                println("failure")
            }
        })
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

}