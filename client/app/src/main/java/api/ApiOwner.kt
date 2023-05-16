package api

import interfaces.OwnerApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiOwner {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(OwnerApi::class.java)
}