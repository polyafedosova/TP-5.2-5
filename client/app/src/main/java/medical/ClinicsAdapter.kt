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
import api.Api
import dto.TreatmentDtoGet
import dto.VetclinicDtoGet
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import ru.vsu.cs.tp.paws.R
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class ClinicsAdapter(_newClinics: MutableList<VetclinicDtoGet>, _lowerPrice: MutableList<BigDecimal>?, _treatment: String) :
    RecyclerView.Adapter<ClinicsAdapter.ClinicsViewHolder>(), Filterable {

    private var treatment = _treatment
    private var lowerPrice: MutableList<BigDecimal>? = _lowerPrice
    private var newClinics: MutableList<VetclinicDtoGet> = _newClinics
    private var newClinicsFull: List<VetclinicDtoGet> = java.util.ArrayList<VetclinicDtoGet>(newClinics)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicsViewHolder {
        val clinicsItems: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.clinics_item, parent, false)
        return ClinicsViewHolder(clinicsItems)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ClinicsViewHolder, position: Int) {
        var pricesStr = ""
        val temp = lowerPrice
        if (lowerPrice != null) {
            for (i in 0 until  lowerPrice!!.size) {
                try {
                    if (i == 0) {
                        pricesStr = "От " + temp!![i].toString() + "₽"
                        lowerPrice!!.remove(lowerPrice!![i])
                    }

                } catch (e: Exception) {
                            println(e)
                }
            }
            holder.clinicsPrice.text = pricesStr
        }


        getTrearments(newClinics[position].id, object : TreatmentCallback {
            override fun onDataReceived(data: List<TreatmentDtoGet>) {
                if (lowerPrice == null) {

                }else {
                    var treatmentStr = ""
                    for (i in 0..data.size - 1) {
                        if (data[i].name.lowercase().contains(treatment.lowercase())){
                            treatmentStr += data[i].name + '|'
                        }
                    }

                    holder.clinicsPreviewTreatment.text = treatmentStr

                }

            }

            override fun onFailure(code: Int, message: String) {

                println("Error: $code, $message")
            }
        })

        holder.clinicsTitle.text = newClinics[position].name
        holder.clinicsAddress.text = newClinics[position].city + " " + newClinics[position].street + " " +newClinics[position].house


        val bundle = Bundle()


            bundle.putInt("id", newClinics[position].id)
            bundle.putString("name", newClinics[position].name)
            bundle.putString("phone", newClinics[position].phone)
            bundle.putString("description", newClinics[position].description)
            bundle.putString("region", newClinics[position].region)
            bundle.putString("city", newClinics[position].city)
            bundle.putString("street", newClinics[position].street)
            bundle.putString("house", newClinics[position].house)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_medicalFragment_to_specificFragment, bundle)
        }
    }

    private fun getTrearments(id: Int, callback: TreatmentCallback) {
        val call = Api.getApiTreatment().getVetclinicTreatments(id)
        call.enqueue(object : Callback<List<TreatmentDtoGet>> {
            override fun onResponse(call: Call<List<TreatmentDtoGet>>, response: Response<List<TreatmentDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse != null) {
                        callback.onDataReceived(dataResponse)
                    } else {
                        callback.onFailure(response.code(), "Empty response body")
                    }
                } else {
                    callback.onFailure(response.code(), response.message())
                }

            }

            override fun onFailure(call: Call<List<TreatmentDtoGet>>, t: Throwable) {
                callback.onFailure(0, t.message ?: "Unknown error")
            }
        })
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
            if (constraint.isEmpty()) {
                filteredList.addAll(newClinicsFull)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (item in newClinicsFull) {
                    if (filterPattern.toLowerCase(Locale.ROOT).contains(filterPattern)) {
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
            clinicsPrice = itemView.findViewById(R.id.clinics_preview_price)

        }

    }


}



