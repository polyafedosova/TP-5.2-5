package event

import android.content.Context
import android.content.SharedPreferences
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
import api.Api
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dto.EventDtoGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate

class EventsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventAdapter
    private lateinit var addEventButton: FloatingActionButton
    private lateinit var addEventButtonBack: FloatingActionButton

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = Api.getApiEvent().getEventsOwner(getLoginFromSharedPreferences(), headers)

        call.enqueue(object : Callback<List<EventDtoGet>> {
            override fun onResponse(call: Call<List<EventDtoGet>>, response: Response<List<EventDtoGet>>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    println(dataResponse)
                    eventsAdapter = EventAdapter(dataResponse as MutableList<EventDtoGet>)
                    recyclerView.adapter = eventsAdapter

                } else {
                    println(response.code())
                    println(response.message())
                    println("Не успешно")
                }
            }

            override fun onFailure(call: Call<List<EventDtoGet>>, t: Throwable) {
                println("Ошибка")
                println(t)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        addEventButton = view.findViewById(R.id.addEventButton)
        addEventButtonBack = view.findViewById(R.id.addEventButtonBack)

        recyclerView = view.findViewById(R.id.recycler_event_events)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        addEventButton.setOnClickListener() {
            it.findNavController().navigate(R.id.action_eventsFragment_to_addEventFragment)
        }
        addEventButtonBack.setOnClickListener() {
            it.findNavController().navigate(R.id.action_eventsFragment_to_profileFragment)
        }

        return view
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

}