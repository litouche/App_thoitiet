package fus.com.weathertoday.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NetworkWeatherCondition(
    var temp: Double,
    @SerializedName("feels_like")
    var feelsLike: Double,
    val pressure: Double,
    val humidity: Double
) : Parcelable
