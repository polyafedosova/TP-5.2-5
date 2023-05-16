package interfaces

import dto.OwnerDtoPost
import retrofit2.Call
import retrofit2.http.*

interface OwnerApi {
//    @GET("owners")
//    fun getAllOwners(): Call<List<OwnerDto>>

    @POST("owner")
    fun findByLogin(login: String) : OwnerDtoPost

    @POST("registration")
    fun saveNewOwner(@Body owner: OwnerDtoPost): Call<Void>

    @PUT("owners/{id}/update")
    fun updateOwner(@Path("id") id: Int, @Body owner: OwnerDtoPost): Call<Void>

    @DELETE("owner/{id}/delete")
    fun deleteOwner(@Path("id") id: Int): Call<Void>
}