package fus.com.weathertoday.mapper

import fus.com.weathertoday.data.model.Weather
import fus.com.weathertoday.data.source.local.entity.DBWeather

class WeatherMapperLocal : BaseMapper<DBWeather, Weather> {
    override fun transformToDomain(type: DBWeather): Weather = Weather(
        uId = type.uId,
        cityId = type.cityId,
        name = type.cityName,
        wind = type.wind,
        networkWeatherDescription = type.networkWeatherDescription,
        networkWeatherCondition = type.networkWeatherCondition,
        sys = type.sys
    )

    override fun transformToDto(type: Weather): DBWeather = DBWeather(
        uId = type.uId,
        cityId = type.cityId,
        cityName = type.name,
        wind = type.wind,
        networkWeatherDescription = type.networkWeatherDescription,
        networkWeatherCondition = type.networkWeatherCondition,
        sys = type.sys
    )
}
