package medical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vsu.cs.tp.paws.R


class MedicalFragment : Fragment() {

//    fun newInstance(): MedicalFragment {
//        return MedicalFragment()
//    }

    private lateinit var searchView: SearchView
    private lateinit var searchViewCity: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var clinicsAdapter: ClinicsAdapter
    private lateinit var currentQuery: String
    private lateinit var listView: ListView
    private lateinit var listViewCity: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_medical, container, false)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        var view: View = inflater.inflate(R.layout.fragment_medical, container, false)

        searchView = view.findViewById(R.id.search_widget)
        searchViewCity = view.findViewById(R.id.search_widget_city)
        listView = view.findViewById<ListView>(R.id.list_item)
        listViewCity = view.findViewById<ListView>(R.id.list_item_city)

        recyclerView = view.findViewById(R.id.recycler_clinics)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        clinicsAdapter = ClinicsAdapter(getDataClinics() as MutableList<ClinicsModel>)
        recyclerView.adapter = clinicsAdapter


//        var adapterSearch = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, getDataSearch())

        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }


    //----------------------ЗАПОЛНЕНИЕ---------------------ДАННЫХ----------------------------------
    private fun getDataSearch(): List<String>? {
        val data: MutableList<String> = java.util.ArrayList()
        data.add("Предоперационный эхо скрининг сердца")
        data.add("УЗИ брюшной полости")
        data.add("Полное обследование сердца")
        data.add("Мочеполовая система")
        data.add("Диагностика беременности")
        data.add("C#")
        data.add("Python")
        data.add("Ruby")
        data.add("JavaScript")
        data.add("PHP")
        data.add("Go")
        data.add("Rust")
        data.add("Scala")
        data.add("Clojure")
        return data
    }

    private fun getDataCity(): List<String>? {
        val city: MutableList<String> = ArrayList()
        city.add("Новосибирск")
        city.add("Екатеринбург")
        city.add("Нижний Новгород")
        city.add("Санкт-Петербург")
        city.add("Москва")
        return city
    }

    private fun getDataClinics(): List<ClinicsModel>? {
        val listClinics: MutableList<ClinicsModel> = java.util.ArrayList()
        listClinics.add(ClinicsModel(1, "Предоперационный эхо Диагностика сердца", "Воронеж", "60"))
        listClinics.add(ClinicsModel(2, "УЗИ брюшной обследование", "Москва", "100"))
        listClinics.add(ClinicsModel(3, "Полное обследование сердца", "Новгород", "1000"))
        listClinics.add(ClinicsModel(4, "Мочеполовая система", "Нижний", "1000"))
        listClinics.add(ClinicsModel(5, "Диагностика беременности", "Екатеринбург", "2000"))
        listClinics.add(ClinicsModel(9, "Диагностика беременности", "Москва", "2000"))
        listClinics.add(ClinicsModel(6, "Диагностика", "Екатеринбург", "5000"))
        listClinics.add(ClinicsModel(7, "УЗИ", "Новосибирск", "8000"))
        listClinics.add(ClinicsModel(8, "обследование", "Новосибирск", "50000"))
        return listClinics
    }

}