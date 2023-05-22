package interfaces

import dto.DogDtoGet
import dto.DogDtoPost
import retrofit2.http.*
import retrofit2.Call


interface DogInterface {
    @POST("/owner/{username}/dogs/new")
    fun saveNewDog(@Path("username") username: String, @Body dog: DogDtoPost, @HeaderMap headers: Map<String, String>): Call<Void?>

    @PUT("/owner/{owner_id}/dogs/{id}/update")
    fun updateDog(@Path("owner_id") owner_id: Int?, @Path("id") id: Int?, @Body dog: DogDtoPost?): Call<Void?>?

    @DELETE("/owner/{owner_id}/dogs/{id}/delete")
    fun deleteDog(@Path("owner_id") owner_id: Int?, @Path("id") id: Int?): Call<Void?>?

    @GET("/owner/{username}/dogs")
    fun getDogsOwner(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Call<List<DogDtoGet>>
}