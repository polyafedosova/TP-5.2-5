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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.ApiVetclinic
import dto.TreatmentDtoGet
import dto.VetclinicDtoGet

import interfaces.VetclinicInterface
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import ru.vsu.cs.tp.paws.R


class MedicalFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var searchViewCity: SearchView
    private lateinit var recyclerView: RecyclerView
    private var clinicsAdapter: ClinicsAdapter? = null
    private lateinit var currentQuery: String
    private lateinit var listView: ListView
    private lateinit var listViewCity: ListView

    private var clinicsList: List<VetclinicDtoGet>? = null

    private var services: List<TreatmentDtoGet>? = null


//    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:8080")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val call = ApiVetclinic.service.getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()

                    clinicsAdapter = ClinicsAdapter(dataResponse as MutableList<VetclinicDtoGet>)
                    recyclerView.adapter = clinicsAdapter
//                    dataResponse.let { processData(it) }
                } else {
                    println("AAAAAAAAAAAAAAAA")
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("BBBBBBBBBBBBBBBBB")
            }
        })
    }

    private fun processData(dataResponse: List<VetclinicDtoGet>) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        var view: View = inflater.inflate(R.layout.fragment_medical, container, false)

        searchView = view.findViewById(R.id.search_widget)
        searchViewCity = view.findViewById(R.id.search_widget_city)
        listView = view.findViewById<ListView>(R.id.list_item)
        listViewCity = view.findViewById<ListView>(R.id.list_item_city)

        recyclerView = view.findViewById(R.id.recycler_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)


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
                clinicsAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterSearch.filter.filter(newText)
                clinicsAdapter?.filter?.filter(newText)
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
                clinicsAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterSearchCity.filter.filter(newText)
                clinicsAdapter?.filter?.filter(newText)
                listViewCity.visibility = View.VISIBLE
                return true
            }
        })


        return view
    }


    private fun getClinicsList(): List<VetclinicDtoGet>? {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Замените на URL вашего сервера
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var data: Call<List<VetclinicDtoGet>>? = null

        val service = retrofit.create(VetclinicInterface::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                data = service.getAllVetclinics() // Замените на необходимый ID пользователя
                // Обработка полученных данных, например:
                // textView.text = "User ID: ${user.id}, Name: ${user.name}"
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
        return data?.execute()?.body()
    }


    private fun getDataSearch(): MutableList<String> {
        val data: MutableList<String> = java.util.ArrayList()
        data.add("Предоперационный эхо скрининг сердца")
        data.add("УЗИ брюшной полости")
        data.add("Полное обследование сердца")
        data.add("Мочеполовая система")
        data.add("Диагностика беременности")
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

}