package medical

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vsu.cs.tp.paws.R


class MedicalFragment : Fragment() {


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        var view: View = inflater.inflate(R.layout.fragment_medical, container, false)

        searchView = view.findViewById(R.id.search_widget)
        searchViewCity = view.findViewById(R.id.search_widget_city)
        listView = view.findViewById<ListView>(R.id.list_item)
        listViewCity = view.findViewById<ListView>(R.id.list_item_city)

        recyclerView = view.findViewById(R.id.recycler_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        clinicsAdapter = ClinicsAdapter(getDataClinics() as MutableList<ClinicsModel>)
        recyclerView.adapter = clinicsAdapter


        var adapterSearch = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, getDataSearch())
        var adapterSearchCity = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, getDataCity())

        view.setOnClickListener { v ->
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            searchView.clearFocus()
            searchViewCity.clearFocus()
        }


        //--------------------------ПОИСК ПО УСЛУГАМ-----------------------------------------
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                listView.visibility = View.VISIBLE
                listView.adapter = adapterSearch
            } else {
                listView.visibility = View.GONE
            }
        }

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                currentQuery = listView.getItemAtPosition(position) as String
                searchView.setQuery(currentQuery, false)
                searchView.clearFocus()
                listView.visibility = View.GONE
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapterSearch.filter.filter(query)
                clinicsAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterSearch.filter.filter(newText)
                clinicsAdapter.filter.filter(newText)
                listView.visibility = View.VISIBLE
                return true
            }
        })

        searchViewCity.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                listViewCity.visibility = View.VISIBLE
                listViewCity.adapter = adapterSearchCity
            } else {
                listViewCity.visibility = View.GONE
            }
        }

        listViewCity.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                currentQuery = listViewCity.getItemAtPosition(position) as String
                searchViewCity.setQuery(currentQuery, false)
                searchViewCity.clearFocus()
                listViewCity.visibility = View.GONE
            }

        searchViewCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapterSearchCity.filter.filter(query)
                clinicsAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterSearchCity.filter.filter(newText)
                clinicsAdapter.filter.filter(newText)
                listViewCity.visibility = View.VISIBLE
                return true
            }
        })


        return view
    }


    private fun getDataSearch(): MutableList<String> {
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

    private fun getDataCity(): List<String> {
        val city: MutableList<String> = ArrayList()
        city.add("Новосибирск")
        city.add("Екатеринбург")
        city.add("Нижний Новгород")
        city.add("Санкт-Петербург")
        city.add("Москва")
        return city
    }

    private fun getDataClinics(): List<ClinicsModel> {
        val listClinics: MutableList<ClinicsModel> = java.util.ArrayList()
        listClinics.add(ClinicsModel(1, "Лаповое","Предоперационный эхо Диагностика сердца", "Воронеж", "60"))
        listClinics.add(ClinicsModel(2, "Крутое название","УЗИ брюшной обследование", "Москва", "100"))
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