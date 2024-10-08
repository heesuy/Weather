package com.example.weather.Model
//import WeatherResponse
//import android.util.Log
//import com.example.weather.Data.RainStatus
//import com.example.weather.Data.SkyStatus
//import com.example.weather.Data.WeatherData
//import com.example.weather.RetrofitInstance.service
//import com.google.gson.annotations.SerializedName
//import java.net.URL
//
//data class WeatherModel(
//    @SerializedName("response")
//    val response: Response
//) {
////    fun toWeatherData(): WeatherData {
////        return response.body.items.item.toWeatherData()
////    }
//
//
//    suspend fun fetchWeatherData(weatherData: WeatherResponse): Pair<String, ByteArray> {
//        val apiKey = "YOUR_API_KEY"
//        //val weatherData = service.getWeather(city = "Seoul", apiKey = apiKey)
//
//        val feelsLike =  weatherData.main.feelsLike - 273.0
//        val temp = weatherData.main.temp - 273.0
//        val tempMin = weatherData.main.tempMin - 273.0
//        val tempMax = weatherData.main.tempMax - 273.0
//        val pressure = weatherData.main.pressure
//        val humidity = weatherData.main.humidity
//        val windSpeed = weatherData.wind.speed
//        val icon = weatherData.weather[0].icon
//
//        var infoStr = "온도: $temp\n"
//        infoStr += "최저온도: $tempMin\n최고온도: $tempMax\n체감온도: $feelsLike\n"
//        infoStr += "기압: $pressure 파스칼\n습도: $humidity%\n풍속: $windSpeed m/sec\n"
//
//        val iconUrl = "https://openweathermap.org/img/wn/${icon}@2x.png"
//        val iconData = URL(iconUrl).readBytes()
//
//        return Pair(infoStr, iconData)
//    }
//
//        private fun toWeatherData(): WeatherData {
//
//            val feelsLike = weatherData.main.feelsLike - 273.0
//            val temp = weatherData.main.temp - 273.0
//            val humidity = weatherData.main.humidity.toString()
//            val windSpeed = weatherData.wind.speed.toString()
//            val icon = weatherData.weather[0].icon
//
//            val time = "--:--" // OpenWeatherMap에서 제공하지 않음
//            val skyStatus = when (icon.substring(0, 2)) {
//                "01", "02" -> SkyStatus.GOOD
//                "03", "04" -> SkyStatus.CLOUDY
//                else -> SkyStatus.VERY_CLOUDY
//            }
//            val rainStatus = when (icon.substring(2, 3)) {
//                "n" -> RainStatus.NONE
//                "r" -> RainStatus.RAIN
//                "s" -> RainStatus.SNOW
//                "rs" -> RainStatus.RAIN_SNOW
//                else -> RainStatus.NONE
//            }
//
//            return WeatherData(
//
//                temp = temp.toString(),
//                windSpeed = windSpeed,
//                humidity =
//            )
//        }
//    }
//
////    private fun List<Item>.toWeatherData(): WeatherData {
////        val items = this
////        val time = items.find { it.category == "SKY" }?.fcstTime ?: "--:--"
////        val skyStatus = items.find { it.category == "SKY" }?.fcstValue ?: ""
////        val rainStatus = items.find { it.category == "PTY" }?.fcstValue ?: ""
////        val rainPercent = items.find { it.category == "POP" }?.fcstValue ?: ""
////        val rainAmount = items.find { it.category == "PCP" }?.fcstValue ?: ""
////        val temp = items.find { it.category == "TMP" }?.fcstValue ?: ""
////        val windSpeed = items.find { it.category == "WSD" }?.fcstValue ?: ""
////        val humidity = items.find { it.category == "REH" }?.fcstValue ?: ""
////
////        return WeatherData(
////            time = time,
////            skyStatus = SkyStatus.entries.firstOrNull { it.value == skyStatus.toInt() }
////                ?: SkyStatus.GOOD,
////            rainAmount = rainAmount,
////            rainPercent = rainPercent,
////            rainState = RainStatus.entries.firstOrNull { it.value == rainStatus.toInt() }
////                ?: RainStatus.NONE,
////            temp = temp,
////            windSpeed = windSpeed,
////            humidity = humidity
////        )
////    }
//
//    fun toWeatherList(count: Int): List<WeatherData> {
//        val list = mutableListOf<WeatherData>()
//        val items = response.body.items.item
//        val baseTime = items.first().baseTime
//        var nextTime = baseTime.nextTime()
//        repeat(count) {
//            val subItems = items.filter { it.fcstTime == nextTime }.toList()
//            val weatherData = subItems.toWeatherData()
//            Log.d("WeatherData", "time: $nextTime, data: $weatherData")
//            list.add(weatherData)
//            nextTime = nextTime.nextTime()
//        }
//        return list
//    }
//
//    private fun String.nextTime(): String {
//        if (this.length != 4) {
//            throw IllegalArgumentException("잘못된 시간 형식")
//        }
//
//        val hour = this.substring(0, 2).toInt()
//        val minute = this.substring(2, 4).toInt()
//
//        if (hour !in 0..23 || minute !in 0..59) {
//            throw IllegalArgumentException("잘못된 시간 범위")
//        }
//
//        val nextHour = if (hour == 23) 0 else hour + 1
//        return "%02d%02d".format(nextHour, minute)
//    }
//}

