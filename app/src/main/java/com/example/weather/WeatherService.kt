package com.example.weather

//import com.example.weather.Model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



//package com.example.howweather.network



//object RetrofitInstance {
//    private const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
////            .client(
////                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
////                    level = HttpLoggingInterceptor.Level.BODY
////                }).build()
////            )
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val service: WeatherService by lazy { retrofit.create(WeatherService::class.java) }
//}


// 배포시에는 안전하게 보완 적용 필요
// 공공 데이터 포탈에서 발급 받은 자신만의 API키를 입력해 주세요.
private const val API_KEY =
    "44ee568572566e2e694290868d9d742c"

//interface WeatherService {
//
//    @GET("getVilageFcst?serviceKey=$API_KEY")
//    suspend fun getWeather(
//        @Query("base_date") baseDate: Int ,
//        @Query("base_time") baseTime: String = "0500",
//        @Query("nx") nx: String = "60",
//        @Query("ny") ny: String = "121",
//        @Query("numOfRows") numOfRows: Int = 12,
//        @Query("pageNo") pageNo: Int = 1,
//        @Query("dataType") dataType: String = "JSON"
//    ): WeatherModel
//}


