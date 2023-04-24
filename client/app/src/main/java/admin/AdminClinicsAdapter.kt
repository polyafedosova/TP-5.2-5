package admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vsu.cs.tp.paws.R

class AdminClinicsAdapter (_newClinics: MutableList<AdminClinicsModel>) :
    RecyclerView.Adapter<AdminClinicsAdapter.AdminClinicsViewHolder>() {

    private var newClinics: MutableList<AdminClinicsModel> = _newClinics

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminClinicsViewHolder {
        val adminClinicsItems: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.clinics_item, parent, false)
        return AdminClinicsViewHolder(adminClinicsItems)
    }

    override fun onBindViewHolder(holder: AdminClinicsViewHolder, position: Int) {
        holder.clinicsTitle.text = newClinics[position].getName()
        holder.clinicsAddress.text = newClinics[position].getAddress()
        holder.clinicsPrice.text = newClinics[position].getPrice()

        val bundle = Bundle()

        bundle.putString("name", newClinics[position].getName())
        bundle.putString("address", newClinics[position].getAddress())
        bundle.putString("phone", newClinics[position].getPhone())
        bundle.putString("services", newClinics[position].getServices())



        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_adminClinicsFragment_to_adminEditClinicFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return newClinics.size
    }


    class AdminClinicsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var clinicsServices: String
        lateinit var clinicsPhone: String
        var clinicsTitle: TextView
        var clinicsAddress: TextView
        var clinicsPrice: TextView


        init {
            super.itemView
            clinicsTitle = itemView.findViewById(R.id.clinics_preview_title)
            clinicsAddress = itemView.findViewById(R.id.clinics_preview_address)
//            clinicsName = itemView.findViewById(R.id.clinics_preview_price)
            clinicsPrice = itemView.findViewById(R.id.clinics_preview_price)

        }

    }

}