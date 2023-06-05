package interfaces

import dto.OwnerDtoPost
import dto.SplashDtoGet
import retrofit2.Call
import retrofit2.http.*

interface GlobalConfigInterface {
    @GET("/splashscreen")
    fun getAll(): Call<List<SplashDtoGet>>

    @FormUrlEncoded
    @POST("/splashscreen/changeShow")
    fun flagToFalse(@Field("username") username: String, @Field("isShow") isShow: Boolean,
                    @HeaderMap headers: Map<String, String>): Call<Void>
}