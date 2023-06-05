package ru.vsu.cs.tp.paws

import dto.TreatmentDtoGet
import dto.TreatmentDtoPost
import interfaces.TreatmentInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal

class ApiTreatmentTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(TreatmentInterface::class.java)

    private val testDto = TreatmentDtoPost("serviceName", BigDecimal(100))

    private val headers = HashMap<String, String>()

    @Test
    fun testSaveTreatment() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.saveNewTreatment(0, testDto, headers)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testUpdateTreatment() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.updateTreatment(0, 0, testDto, headers)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testDeleteTreatment() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.deleteTreatment(0, 0)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testGetAllTreatment() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.getVetclinicTreatments(0)
        call.enqueue(object : Callback<List<TreatmentDtoGet>> {
            override fun onResponse(call: Call<List<TreatmentDtoGet>>, response: Response<List<TreatmentDtoGet>>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<TreatmentDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }

}