package interfaces

import dto.OwnerDto
import retrofit2.Call
import retrofit2.http.*

interface OwnerApi {
//    @GET("owners")
//    fun getAllOwners(): Call<List<OwnerDto>>

    @POST("owners/new")
    fun saveNewOwner(@Body owner: OwnerDto): Call<Void>

    @PUT("owners/{id}/update")
    fun updateOwner(@Path("id") id: Int, @Body owner: OwnerDto): Call<Void>

    @DELETE("owners/{id}/delete")
    fun deleteOwner(@Path("id") id: Int): Call<Void>
}