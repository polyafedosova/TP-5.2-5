package medical

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.ApiVetclinic
import dto.TreatmentDtoGet
import dto.VetclinicDtoGet
import dto.VetclinicSortDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.tp.paws.R
import java.math.BigDecimal


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllClinics()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        var view: View = inflater.inflate(R.layout.fragment_medical, container, false)

        searchView = view.findViewById(R.id.search_treatment)
        searchViewCity = view.findViewById(R.id.search_widget_city)
        listView = view.findViewById<ListView>(R.id.list_item)
        listViewCity = view.findViewById<ListView>(R.id.list_item_city)

        recyclerView = view.findViewById(R.id.recycler_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        view.setOnClickListener { v ->
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            searchView.clearFocus()
            searchViewCity.clearFocus()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != "") {
                    getClinicsByTreatment(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != "") {
                    getClinicsByTreatment(newText)
                }else{
                    getAllClinics()
                }

                return true
            }
        })


        return view
    }

    private fun getClinicsByTreatment(treatment: String) {
        val call = ApiVetclinic.service.sort(treatment, null)

        call.enqueue(object : Callback<List<VetclinicSortDto>> {
            override fun onResponse(call: Call<List<VetclinicSortDto>>, response: Response<List<VetclinicSortDto>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    val sortedClinics: ArrayList<VetclinicDtoGet> = ArrayList()
                    val sortedPrices: ArrayList<BigDecimal> = ArrayList()
                    println(dataResponse)
                    if (dataResponse != null) {
                        for(i in 0..dataResponse.size - 1) {
                            sortedClinics.add(dataResponse[i].vetclinicDto)
                            sortedPrices.add(dataResponse[i].minPrice)
                        }
                    }
                    clinicsAdapter = ClinicsAdapter(sortedClinics as MutableList<VetclinicDtoGet>, sortedPrices)
                    recyclerView.adapter = clinicsAdapter

                } else {
                    println("response not success " + response.code())
                }
            }

            override fun onFailure(call: Call<List<VetclinicSortDto>>, t: Throwable) {
                println("No connect")
            }
        })
    }
    private fun getAllClinics() {
        val call = ApiVetclinic.service.getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()

                    clinicsAdapter = ClinicsAdapter(dataResponse as MutableList<VetclinicDtoGet>, null)
                    recyclerView.adapter = clinicsAdapter

                } else {
                    println("response not success " + response.code())
                }
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }



}