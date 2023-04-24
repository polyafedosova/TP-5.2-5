package admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import medical.ClinicsAdapter
import medical.ClinicsModel
import ru.vsu.cs.tp.paws.R

class AdminClinicsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adminClinicsAdapter: AdminClinicsAdapter

    private lateinit var adminAddClinicButton: FloatingActionButton
    private lateinit var adminLogoutButton: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_admin_clinics, container, false)

        recyclerView = view.findViewById(R.id.recyclerClinics)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adminClinicsAdapter = AdminClinicsAdapter(getDataClinics() as MutableList<AdminClinicsModel>)
        recyclerView.adapter = adminClinicsAdapter

        adminAddClinicButton = view.findViewById(R.id.adminAddClinicButton)
        adminLogoutButton = view.findViewById(R.id.adminLogoutButton)

        adminAddClinicButton.setOnClickListener() {
            it.findNavController().navigate(R.id.action_adminClinicsFragment_to_adminAddClinicFragment)
        }

        adminLogoutButton.setOnClickListener() {
            it.findNavController().navigate(R.id.loginFragment)
        }

        return view
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