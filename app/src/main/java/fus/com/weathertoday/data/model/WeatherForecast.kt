package fus.com.weathertoday.data.model


data class WeatherForecast(
    val uID: Int,

    var dt: Long,

    var date: String,

    val wind: Wind,

    val networkWeatherDescription: List<NetworkWeatherDescription>,

    val networkWeatherCondition: NetworkWeatherCondition
)
