package ru.vsu.cs.tp.paws

import dto.OwnerDtoGet
import dto.OwnerDtoPost
import interfaces.OwnerInterface
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiOwnerTest {
    private val BASE_URL = "http://2.56.242.93:4000"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(OwnerInterface::class.java)

    private val testDto = OwnerDtoPost("login", "password",
        "name", false)

    private val headers = HashMap<String, String>()

    @Test
    fun testFindByLogin() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.findByLogin("login", headers)
        call.enqueue(object : Callback<OwnerDtoGet> {
            override fun onResponse(call: Call<OwnerDtoGet>, response: Response<OwnerDtoGet>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<OwnerDtoGet>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testFindById() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.findById(0, headers)
        call.enqueue(object : Callback<OwnerDtoGet> {
            override fun onResponse(call: Call<OwnerDtoGet>, response: Response<OwnerDtoGet>) {
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
            }

            override fun onFailure(call: Call<OwnerDtoGet>, t: Throwable) {
                println("No connect")
            }
        })
    }

    @Test
    fun testSaveNewUser() {
        val call = apiService.saveNewOwner(testDto)
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
    fun testUpdateUser() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.updateOwner("login", testDto, headers)
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
    fun testDeleteUser() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.deleteOwner(0)
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
    fun testUpdateUserPassword() {
        headers["Authorization"] = "Bearer token"

        val call = apiService.updatePassword("login", "newPass", headers)
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