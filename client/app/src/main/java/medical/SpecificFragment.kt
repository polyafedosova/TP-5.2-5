package medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dto.TreatmentDtoGet
import interfaces.TreatmentApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R

class SpecificFragment : Fragment(){

    private lateinit var specificClinicBackButton: Button
    private lateinit var clinicName: TextView
    private lateinit var clinicAddress: TextView
    private lateinit var clinicServices: TextView

    private var serviceList: List<TreatmentDtoGet>? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_clinic_open, container, false)

        clinicName = view.findViewById(R.id.toolbar_name_text)
        clinicAddress = view.findViewById(R.id.clinicAddress)
        clinicServices = view.findViewById(R.id.servicesTextView)

        val id = requireArguments().getInt("id")
        val nameValue = requireArguments().getString("name")
//        val serviceValue = requireArguments().getString("service")
        val addressValue = requireArguments().getString("address")



        clinicName.text = nameValue
        clinicAddress.text = addressValue

//        clinicServices.text = serviceValue

//        Toast.makeText(this.requireContext(), nameValue, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this.requireContext(), serviceValue, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this.requireContext(), addressValue, Toast.LENGTH_SHORT).show()


        val api = retrofit.create(TreatmentApi::class.java)

//        try {
//            CoroutineScope(Dispatchers.IO).launch {
//
//                requireActivity().runOnUiThread {
//                    serviceList = api.getVetclinicTreatments(id).execute().body()
//                }
//            }
//        } catch(ex: Exception) {
//            ex.stackTrace
//        }

        var tempStr = ""

        for (i in 0..9) {
            tempStr += "name$i  price$i\n"
        }


        clinicServices.text = tempStr

        specificClinicBackButton = view.findViewById(R.id.specificClinicBackButton)

        specificClinicBackButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }
}