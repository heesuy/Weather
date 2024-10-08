package com.example.weather


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.Model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MyPrefKey {
    companion object {
        val setting = stringPreferencesKey("setting")
        //val student_id_key = stringPreferencesKey("student_id")

    }
}

class Repo(private val context: Context) {

    val myPref : Flow<Preferences> = context.dataStore.data
    suspend fun setPref(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        val baseURL = "https://api.openweathermap.org/"
        val api: RestApi = with(Retrofit.Builder()) {
            baseUrl(baseURL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(RestApi::class.java)

        return withContext(Dispatchers.IO) {
            val weatherResponse = api.getWeather()
            Log.d("getWeather", "repository: ${weatherResponse}")
            weatherResponse
        }
    }
}
