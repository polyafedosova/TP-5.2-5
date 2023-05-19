package interfaces

import dto.JwtGet
import dto.JwtPost

import retrofit2.Call
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthInterface {
    @GET("api/auth/token")
    fun getToken(): Response

    @POST("api/auth/login")
    fun login(@Body jwt: JwtPost): Call<JwtGet>

}