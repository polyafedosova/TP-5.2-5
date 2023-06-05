package interfaces

import dto.EventDtoGet
import dto.EventDtoPost
import retrofit2.Call
import retrofit2.http.*

interface EventInterface {
    @POST("/owner/{username}/events/new")
    fun saveNewEvent(@Path("username") username: String, @Body event: EventDtoPost, @HeaderMap headers: Map<String, String>): Call<Void?>

    @PUT("/owner/{username}/events/{id}/update")
    fun updateEvent(@Path("id") id: Int, @Path("username") username: String, @Body event: EventDtoPost,
                    @HeaderMap headers: Map<String, String>): Call<Void?>

    @DELETE("/owner/{username}/events/{id}/delete")
    fun deleteEvent(@Path("id") id: Int, @Path("username") username: String, @HeaderMap headers: Map<String, String>): Call<Void?>

    @GET("/owner/{username}/events")
    fun getEventsOwner(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Call<List<EventDtoGet>>
}