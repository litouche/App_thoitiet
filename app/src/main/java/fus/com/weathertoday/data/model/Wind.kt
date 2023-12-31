package fus.com.weathertoday.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    val speed: Double,
    val deg: Int
) : Parcelable
