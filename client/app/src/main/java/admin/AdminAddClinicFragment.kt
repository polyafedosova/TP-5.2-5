package admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class AdminAddClinicFragment : Fragment() {

    private lateinit var adminAddClinicName: EditText
    private lateinit var adminAddClinicAddress: EditText
    private lateinit var adminAddClinicPhone: EditText
    private lateinit var adminAddClinicServices: EditText

    private lateinit var adminAddClinic: Button
    private lateinit var adminCancelAddClinic: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_add_clinic, container, false)

        adminAddClinicName = view.findViewById(R.id.adminAddClinicName)
        adminAddClinicAddress = view.findViewById(R.id.adminAddClinicAddress)
        adminAddClinicPhone = view.findViewById(R.id.adminAddClinicPhone)
        adminAddClinicServices = view.findViewById(R.id.adminAddClinicServices)

        adminAddClinic = view.findViewById(R.id.adminAddClinic)
        adminCancelAddClinic = view.findViewById(R.id.adminCancelAddClinic)

        adminAddClinic.setOnClickListener() {
            addClinic(adminAddClinicName, adminAddClinicAddress, adminAddClinicPhone, adminAddClinicServices)
        }

        adminCancelAddClinic.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun addClinic(name: EditText, address: EditText, phone: EditText, services: EditText) {

    }

}