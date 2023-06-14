package fus.com.weathertoday.mapper

import fus.com.weathertoday.data.model.WeatherForecast
import fus.com.weathertoday.data.source.local.entity.DBWeatherForecast


class WeatherForecastMapperLocal :
    BaseMapper<List<DBWeatherForecast>, List<WeatherForecast>> {
    override fun transformToDomain(type: List<DBWeatherForecast>): List<WeatherForecast> {
        return type.map { dbWeatherForecast ->
            WeatherForecast(
                dbWeatherForecast.id,
                dbWeatherForecast.dt,
                dbWeatherForecast.date,
                dbWeatherForecast.wind,
                dbWeatherForecast.networkWeatherDescriptions,
                dbWeatherForecast.networkWeatherCondition
            )
        }
    }

    override fun transformToDto(type: List<WeatherForecast>): List<DBWeatherForecast> {
        return type.map { weatherForecast ->
            DBWeatherForecast(
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
