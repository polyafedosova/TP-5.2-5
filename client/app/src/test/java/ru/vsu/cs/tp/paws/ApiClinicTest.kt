package ru.vsu.cs.tp.paws

import api.Api
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import interfaces.VetclinicInterface
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClinicTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(VetclinicInterface::class.java)

    @Test
    fun testGetClinics() {
        val call = apiService.getAllVetclinics()
        call.enqueue(object : Callback<List<VetclinicDtoGet>> {
            override fun onResponse(call: Call<List<VetclinicDtoGet>>, response: Response<List<VetclinicDtoGet>>) {
                assertTrue(response.isSuccessful)
                assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<VetclinicDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testSaveClinic() {
        val dto = VetclinicDtoPost("name", "phone",
            "discription", "заглушка", "region",
            "city", "street", "house")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer token"

        val call = apiService.saveNewVetclinic(dto, headers)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                assertTrue(response.isSuccessful)
                assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("No connect")
            }
        })
    }
}