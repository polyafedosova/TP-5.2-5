package medical

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R

class SpecificFragment : Fragment(){

    private lateinit var specificClinicBackButton: Button
    private lateinit var clinicName: TextView
    private lateinit var clinicAddress: TextView
    private lateinit var clinicServices: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_specific, container, false)

        clinicName = view.findViewById(R.id.toolbar_name_text)
        clinicAddress = view.findViewById(R.id.clinicAddress)
        clinicServices = view.findViewById(R.id.clinicServices)

        val nameValue = requireArguments().getString("name")
        val serviceValue = requireArguments().getString("service")
        val addressValue = requireArguments().getString("address")

        clinicName.text = nameValue
        clinicAddress.text = addressValue
        clinicServices.text = serviceValue

//        Toast.makeText(this.requireContext(), nameValue, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this.requireContext(), serviceValue, Toast.LENGTH_SHORT).show()
//        Toast.makeText(this.requireContext(), addressValue, Toast.LENGTH_SHORT).show()

        specificClinicBackButton = view.findViewById(R.id.specificClinicBackButton)

        specificClinicBackButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }
}