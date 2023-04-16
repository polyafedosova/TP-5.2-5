package event

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.util.Date

class EventsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var addEventButton: FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        addEventButton = view.findViewById(R.id.addEventButton)

        recyclerView = view.findViewById(R.id.recycler_event_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        eventsAdapter = EventsAdapter(getDataEvents() as MutableList<EventsModel>)
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
        val dateString = date.year.toString()+ " " + date.month.value.toString() + " " + date.dayOfMonth.toString()

        listEvents.add(EventsModel(1,"Событие 1", dateString, "Коммент"))
//        listClinics.add(ClinicsModel(2,"Крутое название", "УЗИ брюшной обследование", "Москва", "100"))
//        listClinics.add(ClinicsModel(3, "Полное обследование сердца", "Новгород", "1000"))
//        listClinics.add(ClinicsModel(4, "Мочеполовая система", "Нижний", "1000"))
//        listClinics.add(ClinicsModel(5, "Диагностика беременности", "Екатеринбург", "2000"))
//        listClinics.add(ClinicsModel(9, "Диагностика беременности", "Москва", "2000"))
//        listClinics.add(ClinicsModel(6, "Диагностика", "Екатеринбург", "5000"))
//        listClinics.add(ClinicsModel(7, "УЗИ", "Новосибирск", "8000"))
//        listClinics.add(ClinicsModel(8, "обследование", "Новосибирск", "50000"))
        return listEvents
    }

}