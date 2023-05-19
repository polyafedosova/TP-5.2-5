package interfaces

import dto.OwnerDtoGet
import dto.OwnerDtoPost
import retrofit2.Call
import retrofit2.http.*

interface OwnerInterface {
//    @GET("owners")
//    fun getAllOwners(): Call<List<OwnerDto>>

    @POST("/owner/{username}")
    fun findByLogin(@Path ("username") username: String) : Call<OwnerDtoGet>

    @POST("/owner/{owner_id}")
    fun findById(@Path ("owner_id") id: Int): Call<OwnerDtoGet>
    @POST("registration")
    fun saveNewOwner(@Body owner: OwnerDtoPost): Call<Void>

    @PUT("owners/{id}/update")
    fun updateOwner(@Path("id") id: Int, @Body owner: OwnerDtoPost): Call<Void>

    @DELETE("owner/{id}/delete")
    fun deleteOwner(@Path("id") id: Int): Call<Void>
}