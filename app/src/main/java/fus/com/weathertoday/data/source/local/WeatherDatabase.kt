package fus.com.weathertoday.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fus.com.weathertoday.data.source.local.dao.WeatherDao
import fus.com.weathertoday.data.source.local.entity.DBWeather
import fus.com.weathertoday.data.source.local.entity.DBWeatherForecast
import fus.com.weathertoday.utils.ListNetworkWeatherDescriptionConverter

@Database(entities = [DBWeather::class, DBWeatherForecast::class], version = 1, exportSchema = true)
@TypeConverters(
    ListNetworkWeatherDescriptionConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}
