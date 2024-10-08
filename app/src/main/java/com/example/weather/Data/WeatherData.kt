package com.example.weather.Data
//
//import com.example.weather.R
//
//data class WeatherData(
//    val feelsLike: Double,
//    val temp: Double,
//    val tempMin: Double,
//    val tempMax: Double,
//    val pressure: Double,
//    val humidity: Double,
//    val windSpeed: Double,
//    val icon: String
//)
// {
//
//    override fun toString(): String {
//        return "\n  기온 : $temp 도 " +
//                "\n 습도 : $humidity" +
//                "\n 풍속 : $windSpeed"
//    }
//}
//
//enum class RainStatus(val value: Int, val icon: Int) {
//    NONE(0, R.drawable.ic_sunny),
//    RAIN(1, R.drawable.ic_water),
//    RAIN_SNOW(2, R.drawable.ic_rain_snow),
//    SNOW(3, R.drawable.ic_snow),
//    FALL(4, R.drawable.ic_rainy)
//}
//
//enum class SkyStatus(val value: Int, val text: String, val icon: Int, val colorIcon: Int) {
//    GOOD(1, "맑음", R.drawable.ic_sunny, R.drawable.ic_color_sunny),
//    CLOUDY(3, "구름 많음", R.drawable.ic_sun_cloudy, R.drawable.ic_color_sunny_cloudy),
//    BAD(4, "흐림", R.drawable.ic_cloudy_2, R.drawable.ic_color_cloudy)
//}

data class WeatherData(
    val currentTemperature: Double,
    val currentHumidity: Int,
    val hourlyData: List<HourlyWeatherData>
)

data class HourlyWeatherData(
    val time: String,
    val temperature: Double,
    val humidity: Int,
    val icon: String
)