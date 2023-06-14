package fus.com.weathertoday.ui.weather7days

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fus.com.weathertoday.data.model.WeatherForecast
import fus.com.weathertoday.data.model.WeatherForecastCustom
import fus.com.weathertoday.data.source.repository.WeatherRepository
import fus.com.weathertoday.utils.asLiveData
import fus.com.weathertoday.utils.Result
import kotlinx.coroutines.launch
import java.lang.Integer.min
import javax.inject.Inject

class CityWeatherSevenDaysViewModel @Inject constructor(
    private val repository: WeatherRepository
) :
    ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _weather = MutableLiveData<List<WeatherForecast>?>()
    val weather = _weather.asLiveData()

    private val _weatherCustom = MutableLiveData<ArrayList<WeatherForecastCustom>?>()
    val weatherCustom = _weatherCustom.asLiveData()

    /**
     *This attempts to get the [WeatherForecast] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeatherForecast
     */
    fun getForecastWeather(cityId: Int, isRefresh: Boolean) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = repository.getForecastWeather(cityId, isRefresh)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val weather = result.data
                        _dataFetchState.value = true
                        _weather.value = weather
                        handleDataResponse(result.data)
                        if (isRefresh) {
                            repository.deleteForecastData()
                            repository.storeForecastData(weather)
                        }
                    } else {
                        _dataFetchState.postValue(false)
                        _weather.postValue(listOf())
                        _weatherCustom.postValue(arrayListOf())
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _dataFetchState.value = false
                }

                is Result.Loading -> _isLoading.postValue(true)
            }
        }
    }

    private fun handleDataResponse(data: List<WeatherForecast>) {
        val listData: ArrayList<WeatherForecastCustom> = arrayListOf()
        data.groupBy {
            // Make a YYYY-mm-dd format from YYYY-mm-dd HH:mm:ss format
            it.date.substring(0, min(it.date.length, 10))
        }.toSortedMap().values
            .map {
                listData.add(
                    WeatherForecastCustom(
                        it.first().uID,
                        it.first().dt,
                        it.first().date,
                        it.maxBy { it.networkWeatherCondition.temp }.networkWeatherCondition.temp,
                        it.minBy { it.networkWeatherCondition.temp }.networkWeatherCondition.temp,
                        it.first().networkWeatherDescription.first().icon ?: ""
                    )
                )
            }
        _weatherCustom.value = listData
    }
}
