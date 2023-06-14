package fus.com.weathertoday.data.source.remote

import fus.com.weathertoday.data.model.LocationModel
import fus.com.weathertoday.data.model.NetworkWeather
import fus.com.weathertoday.data.model.NetworkWeatherForecast
import fus.com.weathertoday.utils.Result

interface WeatherRemoteDataSource {
    suspend fun getWeather(location: LocationModel): Result<NetworkWeather>

    suspend fun getWeatherForecast(cityId: Int): Result<List<NetworkWeatherForecast>>

    suspend fun getSearchWeather(query: String):Result<NetworkWeather>
}