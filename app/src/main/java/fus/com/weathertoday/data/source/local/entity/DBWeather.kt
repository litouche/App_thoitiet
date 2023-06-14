package fus.com.weathertoday.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import fus.com.weathertoday.data.model.NetworkWeatherCondition
import fus.com.weathertoday.data.model.NetworkWeatherDescription
import fus.com.weathertoday.data.model.Sys
import fus.com.weathertoday.data.model.Wind

@Entity(tableName = "weather_table")
data class DBWeather(

    @ColumnInfo(name = "unique_id")
    @PrimaryKey(autoGenerate = true)
    var uId: Int = 0,

    @ColumnInfo(name = "city_id")
    val cityId: Int,

    @ColumnInfo(name = "city_name")
    val cityName: String,

    @Embedded
    val wind: Wind,

    @ColumnInfo(name = "weather_details")
    val networkWeatherDescription: List<NetworkWeatherDescription>,

    @Embedded
    val networkWeatherCondition: NetworkWeatherCondition,

    @Embedded
    val sys: Sys
)
