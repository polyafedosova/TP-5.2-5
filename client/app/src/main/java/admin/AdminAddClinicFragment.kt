package admin

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dto.VetclinicDto
import interfaces.VetclinicApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    private lateinit var adminAddClinicServices: EditText

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
        adminAddClinicServices = view.findViewById(R.id.adminAddClinicServices)
        adminAddClinicRegion = view.findViewById(R.id.adminAddClinicRegion)
        adminAddClinicDistrict = view.findViewById(R.id.adminAddClinicDistrict)
        adminAddClinicCity = view.findViewById(R.id.adminAddClinicCity)
        adminAddClinicStreet = view.findViewById(R.id.adminAddClinicStreet)
        adminAddClinicHouse = view.findViewById(R.id.adminAddClinicHouse)

        adminAddClinic = view.findViewById(R.id.adminAddClinic)
        adminCancelAddClinic = view.findViewById(R.id.adminCancelAddClinic)

        adminAddClinic.setOnClickListener() {

            addClinic(adminAddClinicPhone, adminAddClinicName, adminAddClinicCountry,
                adminAddClinicRegion, adminAddClinicDistrict, adminAddClinicCity,
                adminAddClinicStreet, adminAddClinicHouse, adminAddClinicServices)

            it.findNavController().popBackStack()
        }

        adminCancelAddClinic.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun addClinic(phone: EditText, name: EditText,  country: EditText,
                          region: EditText, district: EditText, city: EditText, street: EditText,
                          house: EditText, services: EditText) {

        val api = retrofit.create(VetclinicApi::class.java)
        val dto = VetclinicDto(name.text.toString(), phone.text.toString(),
            services.text.toString(), country.text.toString(), region.text.toString(),
            district.text.toString(), city.text.toString(), street.text.toString(), house.text.toString())

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewVetclinic(dto).execute()
                if (response.code() == 200) {

                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }


    }

}