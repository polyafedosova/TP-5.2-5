package ru.vsu.cs.tp.paws

import api.Api
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import dto.VetclinicSortDto
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

    private val testDto = VetclinicDtoPost("name", "phone",
        "discription", "заглушка", "region",
        "city", "street", "house")

    private val headers = HashMap<String, String>()

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
        headers["Authorization"] = "Bearer token"

        val call = apiService.saveNewVetclinic(testDto, headers)
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

    @Test
    fun testUpdateClinic() {
        val call = apiService.updateVetclinic(0, testDto, headers)
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

    @Test
    fun testDeleteClinic() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.deleteVetclinic(0, headers)
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

    @Test
    fun testSortClinics() {
        val call = apiService.sort("treatment", "city")
        call.enqueue(object : Callback<List<VetclinicSortDto>> {
            override fun onResponse(call: Call<List<VetclinicSortDto>>, response: Response<List<VetclinicSortDto>>) {
                assertTrue(response.isSuccessful)
                assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<VetclinicSortDto>>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testSortByCityClinics() {
        val call = apiService.sortByCity("city")
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

}