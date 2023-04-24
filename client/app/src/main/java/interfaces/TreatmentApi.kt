package interfaces

import dto.TreatmentDto
import retrofit2.Call
import retrofit2.http.*

interface TreatmentApi {
    @POST("/vetclinic/{vetclinic_id}/treatments/new")
    fun saveNewTreatment(@Path("vetclinic_id") vetclinic_id: Int, @Body treatment: TreatmentDto): Call<Void>

    @PUT("/vetclinic/{vetclinic_id}/treatments/{id}/update")
    fun updateTreatment(@Path("vetclinic_id") vetclinic_id: Int, @Path("id") id: Int, @Body treatment: TreatmentDto): Call<Void>

    @DELETE("/vetclinic/{vetclinic_id}/treatments/{id}/delete")
    fun deleteTreatment(@Path("vetclinic_id") vetclinic_id: Int, @Path("id") id: Int): Call<Void>

    @GET("/vetclinic/{vetclinic_id}/treatments")
    fun getVetclinicTreatments(@Path("vetclinic_id") vetclinic_id: Int): Call<List<TreatmentDto>>
}