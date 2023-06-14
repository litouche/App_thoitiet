package fus.com.weathertoday.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import fus.com.weathertoday.data.model.NetworkWeatherCondition
import fus.com.weathertoday.data.model.NetworkWeatherDescription
import fus.com.weathertoday.data.model.Wind

@Entity(tableName = "weather_forecast")
class DBWeatherForecast(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val dt: Long,

    val date: String,

    @Embedded
    val wind: Wind,

    @ColumnInfo(name = "weather_description")
    val networkWeatherDescriptions: List<NetworkWeatherDescription>,

    @Embedded
    val networkWeatherCondition: NetworkWeatherCondition
)
