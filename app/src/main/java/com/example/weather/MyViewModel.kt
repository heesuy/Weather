package com.example.weather

import android.graphics.BitmapFactory
import com.example.weather.Data.HourlyWeatherData
import com.example.weather.Data.WeatherData
import android.util.Log
import android.widget.ImageView
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.Model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

enum class ItemEvent {
    ADD, UPDATE, DELETE, CLEAR
}

class MyViewModel(/*private val repository: Repo*/) : ViewModel() {

    //val myPref = repository.myPref

    val _itemClickEvent = MutableLiveData<Int>()

    private var _hourlyWeatherData = ArrayList<HourlyWeatherData>()
    val hourlyWeatherData get() = _hourlyWeatherData
    var itemsEvent = ItemEvent.ADD
    var itemEventPos = -1
    private val _hourlyData = MutableLiveData<ArrayList<HourlyWeatherData>>()
    val hourlyData: MutableLiveData<ArrayList<HourlyWeatherData>> get() = _hourlyData

    private val _weatherList1 = ArrayList<WeatherData>()
    private val _weatherList = MutableLiveData<ArrayList<WeatherData>>()
    val weatherList: MutableLiveData<ArrayList<WeatherData>> get() = _weatherList


    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: MutableLiveData<WeatherData> get() = _weatherData
//    fun getWeather(city: String = "서울특별시", unit: String = "metric") {
//        viewModelScope.launch {
//            runCatching {
//                Log.d("getWeather", "getWeather start")
//                fetchWeatherData(city,unit)
//            }.onSuccess { weatherResponse ->
//                _weatherData.value = weatherResponse
//                _hourlyWeatherData = _weatherData.value?.hourlyData as ArrayList<HourlyWeatherData>
//                _hourlyData.value = hourlyWeatherData
//                itemsEvent = ItemEvent.ADD
//                itemEventPos = _hourlyData.value!!.size
//                Log.d("getWeather", "ViewModel getWeather data: ${weatherResponse}")
//                Log.d("getWeather", "getWeather Success")
//            }.onFailure { e ->
//                handleException(e)
//                Log.d("getWeather", "getWeather failure")
//            }
//        }
//    }

    fun getWeather(city: String = "서울특별시", unit: String = "metric") {
        viewModelScope.launch {
            runCatching {
                Log.d("getWeather", "getWeather start")
                //fetchWeatherData(city,unit)
                fetchWeatherData1()
            }.onSuccess { weatherResponse ->
                _weatherData.value = weatherResponse
                _hourlyWeatherData = _weatherData.value?.hourlyData as ArrayList<HourlyWeatherData>
                _hourlyData.value = hourlyWeatherData
                itemsEvent = ItemEvent.ADD
                itemEventPos = _hourlyData.value!!.size
                Log.d("getWeather", "ViewModel getWeather data: ${weatherResponse}")
                Log.d("getWeather", "getWeather Success")
            }.onFailure { e ->
                handleException(e)
                Log.d("getWeather", "getWeather failure")
            }
        }
    }







     fun handleException(e: Throwable) {
        Log.d("getWeather", "$e.message")
        when (e) {
            is HttpException -> {
                val errorJsonString = e.response()?.errorBody()?.string()
                Log.d("getWeather", "HTTP error: $errorJsonString")
            }

            is IOException -> Log.e("getWeather", "Network error: $e")
            else -> Log.d("getWeather", "Unexpected error: $e")
        }
    }



//    suspend fun fetchWeatherData(city: String = "Seoul", unit: String = "metric"): WeatherData {
//        val apiKey = "0989eefe6e95e694b87676f7ea82a370"
//        val weatherResponse = repository.getWeather(city, apiKey,unit)
//
//        val currentTemperature = weatherResponse.list[0].main.temp
//        val currentHumidity = weatherResponse.list[0].main.humidity
//
//        val hourlyData = weatherResponse.list.map { hourly ->
//            HourlyWeatherData(
//                time = hourly.dtTxt,
//                temperature = hourly.main.temp,
//                humidity = hourly.main.humidity,
//                icon = hourly.weather[0].icon
//            )
//        }
//
//        return WeatherData(
//            currentTemperature = currentTemperature,
//            currentHumidity = currentHumidity,
//            hourlyData = hourlyData
//        )
//    }

    suspend fun fetchWeatherData1(): WeatherData {
        val weatherResponse = getMockWeatherResponses()

        val currentTemperature = weatherResponse.list[0].main.temp
        val currentHumidity = weatherResponse.list[0].main.humidity

        val hourlyData = weatherResponse.list.map { hourly ->
            HourlyWeatherData(
                time = hourly.dtTxt,
                temperature = hourly.main.temp,
                humidity = hourly.main.humidity,
                icon = hourly.weather[0].icon
            )
        }

        return WeatherData(
            currentTemperature = currentTemperature,
            currentHumidity = currentHumidity,
            hourlyData = hourlyData
        )
    }

//    fun setPref(key: Preferences.Key<String>, value: String) { viewModelScope.launch { // viewmodel 라이프사이클에 맞춘 coroutine scope
//        repository.setPref(key, value) }
//    }


    fun getMockWeatherResponses(): WeatherResponse {
        val weatherData1 = WeatherData(
            dt = 1673846400L,
            main = Main(5.0, 4.5, 3.0, 6.5, 1013, 1013, 1012, 80, 0.5),
            weather = listOf(Weather(800, "Clear", "clear sky", "01d")),
            clouds = Clouds(0),
            wind = Wind(2.1, 180, 3.0),
            visibility = 10000,
            pop = 0.0,
            rain = null,
            sys = Sys("d"),
            dtTxt = "2025-01-17 10:00:00"
        )

        val weatherData2 = WeatherData(
            dt = 1673850000L,
            main = Main(6.5, 5.8, 4.0, 7.5, 1012, 1012, 1011, 75, 0.6),
            weather = listOf(Weather(500, "Rain", "light rain", "10d")),
            clouds = Clouds(20),
            wind = Wind(3.5, 200, 4.5),
            visibility = 9000,
            pop = 0.3,
            rain = Rain(0.2),
            sys = Sys("d"),
            dtTxt = "2025-01-17 11:00:00"
        )

        val weatherData3 = WeatherData(
            dt = 1673853600L,
            main = Main(7.0, 6.0, 5.0, 8.0, 1010, 1010, 1009, 70, 0.7),
            weather = listOf(Weather(801, "Clouds", "few clouds", "02d")),
            clouds = Clouds(40),
            wind = Wind(2.8, 150, 3.2),
            visibility = 8500,
            pop = 0.1,
            rain = null,
            sys = Sys("d"),
            dtTxt = "2025-01-17 12:00:00"
        )

        val city = City(
            id = 1835848,
            name = "Seoul",
            coord = Coord(37.5665, 126.9780),
            country = "KR",
            population = 9776000,
            timezone = 32400,
            sunrise = 1673820000L,
            sunset = 1673853600L
        )

        return(
            WeatherResponse(
                cod = "200",
                message = 0,
                cnt = 3,
                list = listOf(weatherData1, weatherData2, weatherData3),
                city = city
            ))
    }


}
