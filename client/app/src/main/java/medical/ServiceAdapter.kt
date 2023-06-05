package medical

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dto.TreatmentDtoGet
import ru.vsu.cs.tp.paws.R

class ServiceAdapter(private val services: List<TreatmentDtoGet>) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.serviceNameTextView.text = service.name
        holder.servicePriceTextView.text = service.price.toString() + "₽"
    }

    override fun getItemCount(): Int {
        return services.size
    }

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        val servicePriceTextView: TextView = itemView.findViewById(R.id.servicePriceTextView)
    }
}
