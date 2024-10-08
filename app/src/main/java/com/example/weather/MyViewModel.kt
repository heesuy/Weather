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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

enum class ItemEvent {
    ADD, UPDATE, DELETE, CLEAR
}

class MyViewModel(private val repository: Repo) : ViewModel() {

    val myPref = repository.myPref

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
    fun getWeather(city: String = "서울특별시", unit: String = "metric") {
        viewModelScope.launch {
            runCatching {
                Log.d("getWeather", "getWeather start")
                fetchWeatherData(city,unit)
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



    suspend fun fetchWeatherData(city: String = "Seoul", unit: String = "metric"): WeatherData {
        val apiKey = "0989eefe6e95e694b87676f7ea82a370"
        val weatherResponse = repository.getWeather(city, apiKey,unit)

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

    fun setPref(key: Preferences.Key<String>, value: String) { viewModelScope.launch { // viewmodel 라이프사이클에 맞춘 coroutine scope
        repository.setPref(key, value) }
    }

}
