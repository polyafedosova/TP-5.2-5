package event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import medical.ClinicsModel
import ru.vsu.cs.tp.paws.R

class EventsAdapter (_newEvents: MutableList<EventsModel>) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    private var newEvents: MutableList<EventsModel> = _newEvents
    private var newEventsFull: List<EventsModel> = java.util.ArrayList<EventsModel>(newEvents)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        val eventsItems: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventsAdapter.EventsViewHolder(eventsItems)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val clinics1 = newEvents[position]
        holder.eventsTitle.text = newEvents[position].getName()
        holder.eventsData.text = newEvents[position].getDate()
        holder.eventsComment.text = newEvents[position].getComment()

        val bundle = Bundle()

        bundle.putString("name", newEvents[position].getName())

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_eventsFragment_to_specificEventFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return newEvents.size
    }



//        fun publishResults(charSequence: CharSequence, results: FilterResults) {
//            newEvents.clear()
//            newEvents.addAll(results.values as List<ClinicsModel>)
//            notifyDataSetChanged()
//        }


//    init {
//        this.newClinics = newClinics
//        newClinicsFull = ArrayList<Any?>(newClinics)
//    }

    class EventsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var eventsTitle: TextView = itemView.findViewById(R.id.preview_title)
        var eventsData: TextView = itemView.findViewById(R.id.preview_data)
        var eventsComment: TextView = itemView.findViewById(R.id.comment)

    }
}