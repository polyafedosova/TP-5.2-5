package interfaces

import dto.DogDtoGet
import dto.DogDtoPost
import retrofit2.http.*
import retrofit2.Call


interface DogInterface {
    @POST("/owner/{username}/dogs/new")
    fun saveNewDog(@Path("username") username: String, @Body dog: DogDtoPost, @HeaderMap headers: Map<String, String>): Call<Void?>

    @PUT("/owner/{username}/dogs/{id}/update")
    fun updateDog( @Path("id") id: Int?, @Body dog: DogDtoPost?, @Path("username") username: String,
                   @HeaderMap headers: Map<String, String>): Call<Void?>

    @DELETE("/owner/{username}/dogs/{id}/delete")
    fun deleteDog(@Path("id") id: Int?, @Path("username") username: String, @HeaderMap headers: Map<String, String>): Call<Void?>

    @GET("/owner/{username}/dogs")
    fun getDogsOwner(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Call<List<DogDtoGet>>
}