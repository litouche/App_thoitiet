package fus.com.weathertoday.data.source.local

import fus.com.weathertoday.data.source.local.entity.DBWeather
import fus.com.weathertoday.data.source.local.entity.DBWeatherForecast

interface WeatherLocalDataSource {

    suspend fun getWeather(): DBWeather?

    suspend fun saveWeather(weather: DBWeather)

    suspend fun deleteWeather()

    suspend fun getForecastWeather(): List<DBWeatherForecast>?

    suspend fun saveForecastWeather(weatherForecast: DBWeatherForecast)

    suspend fun deleteForecastWeather()
}