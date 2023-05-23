package event

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate

class EventsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventAdapter
    private lateinit var addEventButton: FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        addEventButton = view.findViewById(R.id.addEventButton)

        recyclerView = view.findViewById(R.id.recycler_event_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        eventsAdapter = EventAdapter(getDataEvents() as MutableList<EventsModel>)
        recyclerView.adapter = eventsAdapter

        addEventButton.setOnClickListener() {
            it.findNavController().navigate(R.id.action_eventsFragment_to_addEventFragment)
        }

        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataEvents(): List<EventsModel> {
        val listEvents: MutableList<EventsModel> = java.util.ArrayList()
        val date: LocalDate = LocalDate.of(2023, 7,24)
        val dateString = date.dayOfMonth.toString()+ "." + date.month.value.toString() + "." + date.year.toString()

        listEvents.add(EventsModel(1,"Событие 1", dateString, "сделать что-то"))
        listEvents.add(EventsModel(2,"Событие 2", dateString, "Коммент 2"))

        return listEvents
    }

}