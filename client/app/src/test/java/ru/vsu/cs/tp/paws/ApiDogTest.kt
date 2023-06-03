package ru.vsu.cs.tp.paws

import dto.DogDtoGet
import dto.VetclinicDtoGet
import interfaces.DogInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiDogTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(DogInterface::class.java)

    @Test
    fun testGetDogs() {
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer token"

        val call = apiService.getDogsOwner("login", headers)
        call.enqueue(object : Callback<List<DogDtoGet>> {
            override fun onResponse(call: Call<List<DogDtoGet>>, response: Response<List<DogDtoGet>>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<DogDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }
}