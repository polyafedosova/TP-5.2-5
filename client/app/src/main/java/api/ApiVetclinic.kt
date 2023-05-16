package api

import interfaces.VetclinicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiVetclinic {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(VetclinicApi::class.java)
}