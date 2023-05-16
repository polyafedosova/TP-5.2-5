package interfaces

import dto.VetclinicDtoGet
import dto.VetclinicDtoPost
import retrofit2.Call
import retrofit2.http.*

interface VetclinicApi {
    @GET("/vetclinics")
    fun getAllVetclinics(): Call<List<VetclinicDtoGet>>

    @POST("/vetclinics/new")
    fun saveNewVetclinic(@Body vetclinic: VetclinicDtoPost): Call<Void>

    @PUT("/vetclinics/{id}/update")
    fun updateVetclinic(@Path("id") id: Int, @Body vetclinic: VetclinicDtoPost): Call<Void>

    @DELETE("/vetclinics/{id}/delete")
    fun deleteVetclinic(@Path("id") id: Int): Call<Void>
}
