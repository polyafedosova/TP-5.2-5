package ru.vsu.cs.tp.paws

import dto.DogDtoGet
import dto.DogDtoPost
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
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

    private val testDto = DogDtoPost("name", "birthday",
        true, "breed")

    private val headers = HashMap<String, String>()

    @Test
    fun testGetDogs() {
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

    @Test
    fun testSaveNewDogs() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.saveNewDog("login", testDto, headers)
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
    fun testUpdateDogs() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.updateDog(0,  testDto, "login", headers)
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
    fun testDeleteDogs() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.deleteDog(0, "login", headers)
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