package event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import medical.ClinicsAdapter
import medical.ClinicsModel
import ru.vsu.cs.tp.paws.R

class EventsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        recyclerView = view.findViewById(R.id.recycler_event_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        eventsAdapter = EventsAdapter(getDataEvents() as MutableList<EventsModel>)
        recyclerView.adapter = eventsAdapter

        return view
    }


    private fun getDataEvents(): List<ClinicsModel>? {
        val listClinics: MutableList<ClinicsModel> = java.util.ArrayList()
//        listClinics.add(ClinicsModel(1,"Лаповое", "Предоперационный эхо Диагностика сердца", "Воронеж", "60"))
//        listClinics.add(ClinicsModel(2,"Крутое название", "УЗИ брюшной обследование", "Москва", "100"))
//        listClinics.add(ClinicsModel(3, "Полное обследование сердца", "Новгород", "1000"))
//        listClinics.add(ClinicsModel(4, "Мочеполовая система", "Нижний", "1000"))
//        listClinics.add(ClinicsModel(5, "Диагностика беременности", "Екатеринбург", "2000"))
//        listClinics.add(ClinicsModel(9, "Диагностика беременности", "Москва", "2000"))
//        listClinics.add(ClinicsModel(6, "Диагностика", "Екатеринбург", "5000"))
//        listClinics.add(ClinicsModel(7, "УЗИ", "Новосибирск", "8000"))
//        listClinics.add(ClinicsModel(8, "обследование", "Новосибирск", "50000"))
        return listClinics
    }

}