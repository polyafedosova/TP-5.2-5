package interfaces

import dto.EventDto
import retrofit2.Call
import retrofit2.http.*

interface EventApi {
    @POST("/owner/{owner_id}/events/new")
    suspend fun saveNewEvent(@Path("owner_id") ownerId: Int, @Body event: EventDto)

    @PUT("/owner/{owner_id}/events/{id}/update")
    suspend fun updateEvent(@Path("owner_id") ownerId: Int, @Path("id") id: Int, @Body event: EventDto)

    @DELETE("/owner/{owner_id}/events/{id}/delete")
    suspend fun deleteEvent(@Path("owner_id") ownerId: Int, @Path("id") id: Int)

    @GET("/owner/{owner_id}/events")
    suspend fun getEventsOwner(@Path("owner_id") ownerId: Int): List<EventDto>
}