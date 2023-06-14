package fus.com.weathertoday.data.model


data class WeatherForecastCustom(
    val uID: Int,

    var dt: Long,

    var date: String,

    val minTemp: Double,

    val maxTemp: Double,

    val icon: String
)
