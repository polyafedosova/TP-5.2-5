package interfaces

import dto.OwnerDtoGet
import dto.OwnerDtoPost
import retrofit2.Call
import retrofit2.http.*

interface OwnerInterface {

    @POST("/owner/{username}")
    fun findByLogin(@Path ("username") username: String, @HeaderMap headers: Map<String, String>): Call<OwnerDtoGet>

    @POST("/owner/{owner_id}")
    fun findById(@Path ("owner_id") id: Int): Call<OwnerDtoGet>
    @POST("api/auth/registration")
    fun saveNewOwner(@Body owner: OwnerDtoPost): Call<Void>

    @PUT("owner/{username}/update")
    fun updateOwner(@Path("username") username: String, @Body owner: OwnerDtoPost,
                    @HeaderMap headers: Map<String, String>): Call<Void>

    @DELETE("owner/{id}/delete")
    fun deleteOwner(@Path("id") id: Int): Call<Void>

    @FormUrlEncoded
    @PUT("owner/{username}/update/password")
    fun updatePassword(@Path("username") username: String, @Field("newPassword") newPassword: String,
                       @HeaderMap headers: Map<String, String>): Call<Void>
}