package admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import api.ApiVetclinic
import dto.TreatmentDtoPost
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import interfaces.TreatmentApi
import interfaces.VetclinicApi
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

    var newClinicId = -1

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private lateinit var adminAddClinic: Button
    private lateinit var adminCancelAddClinic: Button

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

        val api = retrofit.create(VetclinicApi::class.java)
        val dto = VetclinicDtoPost(name.text.toString(), phone.text.toString(),
            discription.text.toString(), country.text.toString(), region.text.toString(),
            district.text.toString(), city.text.toString(), street.text.toString(), house.text.toString())
        var flag = false
        try {

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewVetclinic(dto).execute()

                if (response.isSuccessful) {
                    flag = true
                    addTreatments(services)
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
//        if (flag) {
//            Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_LONG).show()
//        }else {
//            Toast.makeText(requireContext(), "Что-то не так", Toast.LENGTH_LONG).show()
//        }
    }

    private fun processData(dataResponse: List<VetclinicDtoGet>) {
        newClinicId = dataResponse[dataResponse.size - 1].id
    }

    private fun addTreatments(services: EditText) {
        val api = retrofit.create(TreatmentApi::class.java)

        val call = ApiVetclinic.service.getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()

                    dataResponse?.let { processData(it) }
                } else {
                    println("AAAAAAAAAAAAAAAA")
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("BBBBBBBBBBBBBBBBB")
            }
        })
//name1,price1;name2,price2

        val prepairList = parseServices(services.toString())
//        [имя1, цена1, имя2, цена2]
        try {
            for (i in 0 until prepairList.size / 2 step 2) {
                val dto = TreatmentDtoPost(prepairList[i], prepairList[i + 1].toBigDecimal())
                api.saveNewTreatment(newClinicId, dto)
            }
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "Что-то не так", Toast.LENGTH_LONG).show()
            ex.stackTrace
        }


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