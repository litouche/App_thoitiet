package fus.com.weathertoday.mapper

import fus.com.weathertoday.data.model.NetworkWeatherForecast
import fus.com.weathertoday.data.model.WeatherForecast


class WeatherForecastMapperRemote :
    BaseMapper<List<NetworkWeatherForecast>, List<WeatherForecast>> {
    override fun transformToDomain(type: List<NetworkWeatherForecast>): List<WeatherForecast> {
        return type.map { networkWeatherForecast ->
            WeatherForecast(
                networkWeatherForecast.id,
                networkWeatherForecast.dt,
                networkWeatherForecast.date,
                networkWeatherForecast.wind,
                networkWeatherForecast.networkWeatherDescription,
                networkWeatherForecast.networkWeatherCondition
            )
        }
    }

    override fun transformToDto(type: List<WeatherForecast>): List<NetworkWeatherForecast> {
        return type.map { weatherForecast ->
            NetworkWeatherForecast(
                weatherForecast.uID,
                weatherForecast.dt,
                weatherForecast.date,
                weatherForecast.wind,
                weatherForecast.networkWeatherDescription,
                weatherForecast.networkWeatherCondition
            )
        }
    }
}
