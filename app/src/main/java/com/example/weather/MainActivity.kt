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
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MyAdapter
//import com.example.howweather.network.API_KEY



//import com.example.weather.Model.WeatherModel
import com.example.weather.Repo


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

    override fun onStart() {
        super.onStart()

    }

    private val currentDate by lazy { SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date()) }
    val myViewModel = MyViewModel(Repo(this))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


//        val connectivityManager =
//            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val nw = connectivityManager.activeNetwork // 현재 활성화된 네트워크
//        val actNw = connectivityManager.getNetworkCapabilities(nw)
//
//        val isConnected = actNw!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//
//        if (isConnected) {
//            Snackbar.make(findViewById(R.id.main), "인터넷 연결", Snackbar.LENGTH_SHORT)
//        }

//        findViewById<Button>(R.id.buttonQuery).setOnClickListener {
//            //downloadImage()
//            refreshRetrofit(findViewById<EditText>(R.id.editUsername).text.toString())
//
//        }
        val adapter = MyAdapter(myViewModel)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

//        myViewModel.weatherList.observe(this) {
//            println(it.size)
//            Log.d("observer","${it.size}")
//            Log.d("getWeather", "getWeather  observer WeatherList ${it}")
//
//            when(myViewModel.itemsEvent){
//                ItemEvent.ADD->adapter.notifyItemInserted(myViewModel.itemEventPos)
//                ItemEvent.DELETE->adapter.notifyItemRemoved(myViewModel.itemEventPos)
//                ItemEvent.UPDATE-> println("nothing update")
//                ItemEvent.CLEAR->adapter.notifyDataSetChanged()
//            }
//
//
//        }

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
//        myViewModel.weatherData.observe(this) {
//            findViewById<TextView>(R.id.textView3).text = it.temp
//            println(it)
//            Log.d("observer","${it}")
//            //adapter.notifyDataSetChanged()
//            println("getItemCOunt ${it}")
//            Log.d("getWeather", "getWeather  observer WeatherList ${it}")
//
//            when(myViewModel.itemsEvent){
//                ItemEvent.ADD->adapter.notifyItemInserted(myViewModel.itemEventPos)
//                ItemEvent.DELETE->adapter.notifyItemRemoved(myViewModel.itemEventPos)
//                ItemEvent.UPDATE-> println("nothing update")
//                ItemEvent.CLEAR->adapter.notifyDataSetChanged()
//            }}


        myViewModel._itemClickEvent.observe(this) {
            val hourly =myViewModel.hourlyWeatherData.get(it)
            Log.d("getWeather", "getWeather  observer itemClickEvent ${it}")

            downloadImage(hourly.icon)

        }



        myViewModel.getWeather(currentDate)

        Log.d("observer","after observer")

    }






    fun formatTimeString(): String? {
        val currentDate = java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.getDefault())
            .format(java.util.Date())
        return currentDate
    }

    fun downloadImage(imageUrl : String) {
        val imageView = findViewById<ImageView>(R.id.imageView3)
        val url = "https://openweathermap.org/img/wn/${imageUrl}@2x.png"
                    // 메인쓰레드 안건들게 DIspatchers.IO
        CoroutineScope(Dispatchers.IO).launch {
            val conn = URL(url).openConnection() as HttpURLConnection

            val isStream = conn.inputStream // 연결시작 입력 스트림 생성!
            val rImg = BitmapFactory.decodeStream(isStream) //데이터가 도착하면 입력스트림에서 받아옴
            withContext(Dispatchers.Main) {
                Log.d("getWeather", "getWeather  imageURL WeatherList ${rImg}")

                imageView.setImageBitmap(rImg)
            }
            conn.disconnect()

        }

}}


