package fus.com.weathertoday.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val uId: Int,
    val cityId: Int,
    val name: String,
    val wind: Wind,
    val networkWeatherDescription: List<NetworkWeatherDescription>,
    val networkWeatherCondition: NetworkWeatherCondition,
    val sys: Sys
) : Parcelable
