package medical

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import api.Api
import com.yandex.metrica.YandexMetrica
import dto.TreatmentDtoGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R

class SpecificFragment : Fragment(){

    private lateinit var specificClinicBackButton: Button
    private lateinit var clinicName: TextView
    private lateinit var clinicAddress: TextView
    private lateinit var clinicServices: TextView
    private lateinit var clinicCityDiscription: TextView
    private lateinit var clinicPhone: TextView

    private lateinit var sharedPreferencesToken: SharedPreferences

    private var serviceList: List<TreatmentDtoGet>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        YandexMetrica.reportEvent("Пользователь перешёл на экран клиники")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_clinic_open, container, false)

        clinicName = view.findViewById(R.id.toolbar_name_text)
        clinicAddress = view.findViewById(R.id.clinicAddress)
        clinicServices = view.findViewById(R.id.servicesTextView)
        clinicPhone = view.findViewById(R.id.clinicPhone)
        clinicCityDiscription = view.findViewById(R.id.clinicCityDiscription)

        val idValue = requireArguments().getInt("id")
        val nameValue = requireArguments().getString("name")
        val phoneValue = requireArguments().getString("phone")
        val descriptionValue = requireArguments().getString("description")
        val regionValue = requireArguments().getString("region")
        val cityValue = requireArguments().getString("city")
        val streetValue = requireArguments().getString("street")
        val houseValue = requireArguments().getString("house")

        val address = "$regionValue,$cityValue,$streetValue,$houseValue"

        clinicName.text = nameValue
        clinicAddress.text = address
        clinicPhone.text = phoneValue
        clinicCityDiscription.text = descriptionValue

        getTrearments(idValue)

        specificClinicBackButton = view.findViewById(R.id.specificClinicBackButton)

        specificClinicBackButton.setOnClickListener {
            it.findNavController().navigate(R.id.medicalFragment)
        }

        return view
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
                            servicesString += dataResponse[i].name + ": " + dataResponse[i].price + ";" + '\n'

                        }
                        clinicServices.text = servicesString
                    }
                } else {
                    println(response.code())
                    println(response.message())
                }
            }

            override fun onFailure(call: Call<List<TreatmentDtoGet>>, t: Throwable) {
                println("failure")
                println(t)
            }
        })
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }
}