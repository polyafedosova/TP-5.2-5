package interfaces

import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import retrofit2.Call
import retrofit2.http.*

interface VetclinicInterface {
    @GET("/vetclinics")
    fun getAllVetclinics(): Call<List<VetclinicDtoGet>>

    @POST("/vetclinics/edit/new")
    fun saveNewVetclinic(@Body vetclinic: VetclinicDtoPost, @HeaderMap headers: Map<String, String>): Call<Void>

    @PUT("/vetclinics/{id}/update")
    fun updateVetclinic(@Path("id") id: Int, @Body vetclinic: VetclinicDtoPost): Call<Void>

    @DELETE("/vetclinics/edit/{id}/delete")
    fun deleteVetclinic(@Path("id") id: Int, @HeaderMap headers: Map<String, String>): Call<Void>
}
