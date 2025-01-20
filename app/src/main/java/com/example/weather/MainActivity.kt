package com.example.weather
import android.content.Context
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MyAdapter
//import com.example.howweather.network.API_KEY



//import com.example.weather.Model.WeatherModel
//import com.example.weather.Repo


import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {



    private val currentDate by lazy { SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date()) }
    val myViewModel = MyViewModel(/*Repo(this)*/)
    var setting = "metric"
    lateinit var switch1: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        switch1 = findViewById(R.id.switch1)
        val adapter = MyAdapter(myViewModel)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)



        myViewModel.hourlyData.observe(this) {
            println(it.size)
            Log.d("observer","${it.size}")
            Log.d("getWeather", "getWeather  observer WeatherList ${it}")

            when(myViewModel.itemsEvent){
                ItemEvent.ADD->adapter.notifyItemInserted(myViewModel.itemEventPos)
                ItemEvent.DELETE->adapter.notifyItemRemoved(myViewModel.itemEventPos)
                ItemEvent.UPDATE-> println("nothing update")
                ItemEvent.CLEAR->adapter.notifyDataSetChanged()
            }


        }



        myViewModel._itemClickEvent.observe(this) {
            val hourly =myViewModel.hourlyWeatherData.get(it)
            Log.d("getWeather", "getWeather  observer itemClickEvent ${it}")


        }







        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setting = "imperial"
                switch1.setText("Farenheit")
                Toast.makeText(this, "화씨로 변환", Toast.LENGTH_SHORT).show()
            } else {
                setting = "metric"
                switch1.setText("Celsius")
                Toast.makeText(this, "섭씨로 변환", Toast.LENGTH_SHORT).show()
            }

        }
        myViewModel.getWeather()







    }

    fun formatTimeString(): String? {
        val currentDate =
            java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.getDefault())
                .format(java.util.Date())
        return currentDate
    }



}
