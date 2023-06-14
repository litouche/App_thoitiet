package fus.com.weathertoday.data.source.repository

import fus.com.weathertoday.data.model.LocationModel
import fus.com.weathertoday.data.model.Weather
import fus.com.weathertoday.data.model.WeatherForecast
import fus.com.weathertoday.utils.Result

interface WeatherRepository {

    suspend fun getWeather(location: LocationModel, refresh: Boolean): Result<Weather?>

    suspend fun getForecastWeather(cityId: Int, refresh: Boolean): Result<List<WeatherForecast>?>

    suspend fun getSearchWeather(location: String): Result<Weather?>

    suspend fun storeWeatherData(weather: Weather)

    suspend fun storeForecastData(forecasts: List<WeatherForecast>)

    suspend fun deleteWeatherData()

    suspend fun deleteForecastData()

}