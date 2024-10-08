package com.example.weather

import com.example.weather.Model.WeatherResponse
//import com.example.weather.Model.WeatherModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {


    //@GET("data/3.0/onecall")
    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lon") lon: String = "127.04",
        @Query("lat") lat: String = "37.517",
        @Query("appid") apiKey: String = "0989eefe6e95e694b87676f7ea82a370",
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("img/wn/{weatherIcon}@2x.png")
    suspend fun getIcon(@Path("weatherIcon") weatherIcon: String): ResponseBody

    @GET("users/{username}/repos")
    fun listReposCall(@Path("username") user: String): Call<ArrayList<Repo>> // 콜백 사용할거면 코루틴 스코프 필요 없어!
    @GET("wp-content/uploads/2019/03/google_logo_download_thumbnail.png")
    suspend fun getImage(): ResponseBody
}