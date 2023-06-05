package ru.vsu.cs.tp.paws

import dto.EventDtoGet
import dto.EventDtoPost
import interfaces.EventInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiEventTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(EventInterface::class.java)

    private val testDto = EventDtoPost("name", "date",
        "time", "description")

    private val headers = HashMap<String, String>()

    @Test
    fun testSaveEvent() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.saveNewEvent("login" ,testDto, headers)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testGetEvents() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.getEventsOwner("login", headers)
        call.enqueue(object : Callback<List<EventDtoGet>> {
            override fun onResponse(call: Call<List<EventDtoGet>>, response: Response<List<EventDtoGet>>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<EventDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testUpdateEvent() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.updateEvent(0,"login", testDto, headers)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testDeleteEvent() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.deleteEvent(0,"login", headers)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                println("No connect")
            }
        })
    }

}