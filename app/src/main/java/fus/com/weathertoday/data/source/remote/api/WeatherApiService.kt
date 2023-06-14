package fus.com.weathertoday.data.source.remote.api

import fus.com.weathertoday.data.model.NetworkWeather
import fus.com.weathertoday.data.model.NetworkWeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    @GET("/data/2.5/weather")
    suspend fun getSpecificWeather(
        @Query("q") location: String,
        @Query("lang") language: String
    ): Response<NetworkWeather>

    // This function gets the weather information for the user's location.
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String
    ): Response<NetworkWeather>

    // This function gets the weather forecast information for the user's location.
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("id") cityId: Int,
        @Query("lang") language: String
    ): Response<NetworkWeatherForecastResponse>
}