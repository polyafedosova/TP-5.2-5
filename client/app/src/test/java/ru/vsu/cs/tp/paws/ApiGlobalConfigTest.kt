package ru.vsu.cs.tp.paws

import dto.SplashDtoGet
import dto.VetclinicDtoPost
import interfaces.GlobalConfigInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiGlobalConfigTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(GlobalConfigInterface::class.java)

    private val headers = HashMap<String, String>()

    @Test
    fun testGetAllScreens() {
        val call = apiService.getAll()
        call.enqueue(object : Callback<List<SplashDtoGet>> {
            override fun onResponse(call: Call<List<SplashDtoGet>>, response: Response<List<SplashDtoGet>>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<List<SplashDtoGet>>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testChangeFlag() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.flagToFalse("login", false, headers)
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

}