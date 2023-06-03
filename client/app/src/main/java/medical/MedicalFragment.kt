package medical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.Api

import dto.VetclinicDtoGet
import dto.VetclinicSortDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.tp.paws.MainActivity
import ru.vsu.cs.tp.paws.R
import java.math.BigDecimal


class MedicalFragment : Fragment() {

    lateinit var searchView: SearchView
    private lateinit var searchViewCity: SearchView
    lateinit var recyclerView: RecyclerView
    var clinicsAdapter: ClinicsAdapter? = null
    private lateinit var listView: ListView
    private lateinit var listViewCity: ListView
    private lateinit var alert: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllClinics()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        val view: View = inflater.inflate(R.layout.fragment_medical, container, false)

        searchView = view.findViewById(R.id.search_treatment)

        searchViewCity = view.findViewById(R.id.search_widget_city)
        listView = view.findViewById<ListView>(R.id.list_item)
        listViewCity = view.findViewById<ListView>(R.id.list_item_city)

        recyclerView = view.findViewById(R.id.recycler_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        alert = view.findViewById(R.id.alert)
        alert.text = ""
        searchViewCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != "") {
                    val noSpaces = query.replace(" ", "")
                    getClinicsByCity(noSpaces)
                    searchView.setQuery("", false)
                }else{
                    getAllClinics()
                }

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })

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


    private fun getClinicsByCity(city: String) {
        val call = Api.getApiVetclinic().sortByCity(city)

        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    val sortedClinics: ArrayList<VetclinicDtoGet> = ArrayList()

                    if (dataResponse != null) {
                        if (dataResponse.size == 0) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(requireContext(), "Клиники не найдены", Toast.LENGTH_SHORT).show()
                            }
                        }
                        for(i in 0..dataResponse.size - 1) {
                            sortedClinics.add(dataResponse[i])
                        }
                    }
                    clinicsAdapter = ClinicsAdapter(sortedClinics as MutableList<VetclinicDtoGet>, null, "")
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

    private fun getClinicsByTreatment(treatment: String) {
        val call = Api.getApiVetclinic().sort(treatment, null)

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
                    if (dataResponse?.size == 0) {
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Клиники не найдены", Toast.LENGTH_SHORT).show()
                        }
                    }
                    clinicsAdapter = ClinicsAdapter(sortedClinics as MutableList<VetclinicDtoGet>, sortedPrices, treatment)
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
    fun getAllClinics() {
        val call = Api.getApiVetclinic().getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    if (dataResponse?.size == 0) {
                        alert.text = "Клиник пока нет"
                    }
                    clinicsAdapter = ClinicsAdapter(dataResponse as MutableList<VetclinicDtoGet>, null, "")
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