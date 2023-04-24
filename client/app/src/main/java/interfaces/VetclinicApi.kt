package interfaces

import dto.VetclinicDto
import retrofit2.Call
import retrofit2.http.*

interface VetclinicApi {
    @GET("/vetclinics")
    fun getAllVetclinics(): Call<List<VetclinicDto>>

    @POST("/vetclinics/new")
    fun saveNewVetclinic(@Body vetclinic: VetclinicDto): Call<Void>

    @PUT("/vetclinics/{id}/update")
    fun updateVetclinic(@Path("id") id: Int, @Body vetclinic: VetclinicDto): Call<Void>

    @DELETE("/vetclinics/{id}/delete")
    fun deleteVetclinic(@Path("id") id: Int): Call<Void>
}
