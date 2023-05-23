package event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dto.EventDtoGet
import ru.vsu.cs.tp.paws.R

class EventAdapter (_newEvents: MutableList<EventDtoGet>) : RecyclerView.Adapter<EventAdapter.EventsViewHolder>() {

    private var newEvents: MutableList<EventDtoGet> = _newEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val eventsItems: View = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventsViewHolder(eventsItems)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.eventsTitle.text = newEvents[position].name
        holder.eventsData.text = newEvents[position].date
        holder.eventsComment.text = newEvents[position].description

        val bundle = Bundle()

        bundle.putInt("id", newEvents[position].id)
        bundle.putString("name", newEvents[position].name)
        bundle.putString("date", newEvents[position].date)
        bundle.putString("comment", newEvents[position].description)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_eventsFragment_to_specificEventFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return newEvents.size
    }


    class EventsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var eventsTitle: TextView = itemView.findViewById(R.id.preview_title)
        var eventsData: TextView = itemView.findViewById(R.id.preview_data)
        var eventsComment: TextView = itemView.findViewById(R.id.comment)

    }
}