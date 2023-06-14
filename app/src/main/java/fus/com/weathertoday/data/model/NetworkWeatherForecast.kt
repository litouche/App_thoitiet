package fus.com.weathertoday.data.model

import com.google.gson.annotations.SerializedName

data class NetworkWeatherForecast(

    val id: Int,

    val dt: Long,

    @SerializedName("dt_txt")
    val date: String,

    val wind: Wind,

    @SerializedName("weather")
    val networkWeatherDescription: List<NetworkWeatherDescription>,

    @SerializedName("main")
    val networkWeatherCondition: NetworkWeatherCondition
)
