package interfaces

import dto.DogDto
import retrofit2.http.*
import retrofit2.Call


interface DogApi {
    @POST("/owner/{owner_id}/dogs/new")
    fun saveNewDog(@Path("owner_id") owner_id: Int?, @Body dog: DogDto?): Call<Void?>?

    @PUT("/owner/{owner_id}/dogs/{id}/update")
    fun updateDog(@Path("owner_id") owner_id: Int?, @Path("id") id: Int?, @Body dog: DogDto?): Call<Void?>?

    @DELETE("/owner/{owner_id}/dogs/{id}/delete")
    fun deleteDog(@Path("owner_id") owner_id: Int?, @Path("id") id: Int?): Call<Void?>?

    @GET("/owner/{owner_id}/dogs")
    fun getDogsOwner(@Path("owner_id") owner_id: Int?): Call<List<DogDto?>?>?
}