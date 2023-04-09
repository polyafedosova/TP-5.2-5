package medical


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val clinics1 = newClinics[position]
        holder.clinicsTitle.text = newClinics[position].getTitle()
        holder.clinicsAddress.text = newClinics[position].getAddress()
        holder.clinicsPrice.text = newClinics[position].getPrice()
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, holder.clinicsTitle.text, Toast.LENGTH_SHORT).show()

            val fragmentManager = (v.context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragment_container, SpecificFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
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

    class ClinicsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var clinicsTitle: TextView = itemView.findViewById(R.id.clinics_preview_title)
        var clinicsAddress: TextView = itemView.findViewById(R.id.clinics_preview_address)
        var clinicsPrice: TextView = itemView.findViewById(R.id.clinics_preview_price)

    }
}
