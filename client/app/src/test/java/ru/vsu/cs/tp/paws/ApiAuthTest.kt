package ru.vsu.cs.tp.paws

import dto.JwtGet
import dto.JwtPost
import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import interfaces.AuthInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAuthTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(AuthInterface::class.java)

    private val testDto = JwtPost("login", "password")

    @Test
    fun testLogin() {
        val call = apiService.login(testDto)
        call.enqueue(object : Callback<JwtGet> {
            override fun onResponse(call: Call<JwtGet>, response: Response<JwtGet>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<JwtGet>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testGetToken() {
        val call = apiService.getToken()
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