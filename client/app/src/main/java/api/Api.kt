package api

import interfaces.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiAuth = retrofit.create(AuthInterface::class.java)
    private val apiDog = retrofit.create(DogInterface::class.java)
    private val apiEvent = retrofit.create(EventInterface::class.java)
    private val apiOwner = retrofit.create(OwnerInterface::class.java)
    private val apiTreatment = retrofit.create(TreatmentInterface::class.java)
    private val apiVetclinic = retrofit.create(VetclinicInterface::class.java)

    fun getApiAuth(): AuthInterface {
        return apiAuth
    }
    fun getApiDog(): DogInterface? {
        return apiDog
    }
    fun getApiEvent(): EventInterface {
        return apiEvent
    }
    fun getApiOwner(): OwnerInterface {
        return apiOwner
    }
    fun getApiTreatment(): TreatmentInterface {
        return apiTreatment
    }
    fun getApiVetclinic(): VetclinicInterface {
        return apiVetclinic
    }


}