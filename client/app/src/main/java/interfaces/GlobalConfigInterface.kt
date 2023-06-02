package interfaces

import dto.SplashDtoGet
import retrofit2.Call
import retrofit2.http.GET

interface GlobalConfigInterface {
    @GET("/splashscreen")
    fun getAll(): Call<List<SplashDtoGet>>
}