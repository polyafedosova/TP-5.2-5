package medical


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dto.VetclinicDtoGet


import ru.vsu.cs.tp.paws.R


class ClinicsAdapter(_newClinics: MutableList<VetclinicDtoGet>) : RecyclerView.Adapter<ClinicsAdapter.ClinicsViewHolder>(), Filterable {

    private var newClinics: MutableList<VetclinicDtoGet> = _newClinics
    private var newClinicsFull: List<VetclinicDtoGet> = java.util.ArrayList<VetclinicDtoGet>(newClinics)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicsViewHolder {
        val clinicsItems: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.clinics_item, parent, false)
        return ClinicsViewHolder(clinicsItems)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ClinicsViewHolder, position: Int) {
//        println(newClinics)

        holder.clinicsTitle.text = newClinics[position].name
        holder.clinicsAddress.text = newClinics[position].street + " " +newClinics[position].house
        holder.clinicsPrice.text = /*newClinics[position].phone*/ "price"
        holder.clinicsPreviewTreatment.text = newClinics[position].phone
        val bundle = Bundle()


            bundle.putInt("id", newClinics[position].id)
            bundle.putString("name", newClinics[position].name)
            bundle.putString("service", "test")
            bundle.putString("address", newClinics[position].street + newClinics[position].house)


        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_medicalFragment_to_specificFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return newClinics.size
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private var searchFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<VetclinicDtoGet?> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(newClinicsFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in newClinicsFull) {
                    if (item.description.toLowerCase().contains(filterPattern) || item.street
                        .toLowerCase().contains(filterPattern)  //спросить что с адресом и как работают услуги
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(charSequence: CharSequence, results: FilterResults) {
            newClinics.clear()
            newClinics.addAll(results.values as List<VetclinicDtoGet>)
            notifyDataSetChanged()
        }
    }


    class ClinicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clinicsTitle: TextView
        var clinicsAddress: TextView
        var clinicsPrice: TextView
        var clinicsPreviewTreatment: TextView


        init {
            super.itemView
            clinicsTitle = itemView.findViewById(R.id.clinics_preview_title)
            clinicsAddress = itemView.findViewById(R.id.clinics_preview_address)
            clinicsPreviewTreatment = itemView.findViewById(R.id.clinics_preview_treatment)
//            clinicsName = itemView.findViewById(R.id.clinics_preview_price)
            clinicsPrice = itemView.findViewById(R.id.clinics_preview_price)

        }

    }


}



