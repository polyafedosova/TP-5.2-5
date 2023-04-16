package medical


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vsu.cs.tp.paws.R


class ClinicsAdapter(_newClinics: MutableList<ClinicsModel>) : RecyclerView.Adapter<ClinicsAdapter.ClinicsViewHolder>(), Filterable {

    private var newClinics: MutableList<ClinicsModel> = _newClinics
    private var newClinicsFull: List<ClinicsModel> = java.util.ArrayList<ClinicsModel>(newClinics)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicsViewHolder {
        val clinicsItems: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.clinics_item, parent, false)
        return ClinicsViewHolder(clinicsItems)
    }

    override fun onBindViewHolder(holder: ClinicsViewHolder, position: Int) {
        holder.clinicsTitle.text = newClinics[position].getTitle()
        holder.clinicsAddress.text = newClinics[position].getAddress()
        holder.clinicsPrice.text = newClinics[position].getPrice()
//        holder.clinicsName.text = newClinics[position].getName()

        val bundle = Bundle()

        bundle.putString("name", newClinics[position].getName())
        bundle.putString("service", newClinics[position].getTitle())
        bundle.putString("address", newClinics[position].getAddress())


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
            val filteredList: MutableList<ClinicsModel?> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(newClinicsFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in newClinicsFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern) || item.getAddress()
                            .toLowerCase().contains(filterPattern)
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
            newClinics.addAll(results.values as List<ClinicsModel>)
            notifyDataSetChanged()
        }
    }

//    init {
//        this.newClinics = newClinics
//        newClinicsFull = ArrayList<Any?>(newClinics)
//    }

    class ClinicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var clinicsTitle: TextView
        var clinicsAddress: TextView
        var clinicsPrice: TextView
//        val clinicsName: TextView


        init {
            super.itemView
            clinicsTitle = itemView.findViewById(R.id.clinics_preview_title)
            clinicsAddress = itemView.findViewById(R.id.clinics_preview_address)
//            clinicsName = itemView.findViewById(R.id.clinics_preview_price)
            clinicsPrice = itemView.findViewById(R.id.clinics_preview_price)

        }

    }


}



