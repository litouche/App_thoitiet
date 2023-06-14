package fus.com.weathertoday.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fus.com.weathertoday.data.model.LocationModel
import fus.com.weathertoday.data.model.Weather
import fus.com.weathertoday.data.model.WeatherForecast
import fus.com.weathertoday.data.source.repository.WeatherRepository
import fus.com.weathertoday.utils.LocationLiveData
import fus.com.weathertoday.utils.Result
import fus.com.weathertoday.utils.asLiveData
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val repository: WeatherRepository
) :
    ViewModel() {

    @Inject
    lateinit var locationLiveData: LocationLiveData

    init {
        currentSystemTime()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading.asLiveData()

    private val _dataFetchState = MutableLiveData<Boolean>()
    val dataFetchState = _dataFetchState.asLiveData()

    private val _weather = MutableLiveData<Weather?>()
    val weather = _weather.asLiveData()

    private val _weatherForecast = MutableLiveData<List<WeatherForecast>?>()
    val weatherForecast = _weatherForecast.asLiveData()

    fun fetchLocationLiveData() = locationLiveData

    /**
     *This attempts to get the [Weather] from the local data source,
     * if the result is null, it gets from the remote source.
     * @see refreshWeather
     */
    fun getWeather(location: LocationModel) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = repository.getWeather(location, false)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val weather = result.data
                        _dataFetchState.value = true
                        _weather.value = weather
                        getWeatherForecast(weather.cityId, false)
                    } else {
                        refreshWeather(location)
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

    @SuppressLint("SimpleDateFormat")
    fun currentSystemTime(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm aaa", Locale("vi", "VN"))
        return dateFormat.format(date)
    }

    fun currentSystemDate(): String {
        val currentTime = System.currentTimeMillis()
        val date = Date(currentTime)
        val dateFormat = SimpleDateFormat("hh:mm, EEEE, dd 'tháng' MM", Locale("vi", "VN"))
        return dateFormat.format(date)
    }

    /**
     * This is called when the user swipes down to refresh.
     * This enables the [Weather] for the current [location] to be received.
     */
    fun refreshWeather(location: LocationModel) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getWeather(location, true)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        val weather = result.data
                        _dataFetchState.value = true
                        _weather.value = weather
                        repository.deleteWeatherData()
                        repository.storeWeatherData(weather)
                        getWeatherForecast(weather.cityId, true)
                    } else {
                        _weather.postValue(null)
                        _dataFetchState.postValue(false)
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

    /**
     *This attempts to get the [WeatherForecast] from the local data source,
     * if the result is null, it gets from the remote source.
     */
    fun getWeatherForecast(cityId: Int, isRefresh: Boolean) {
        viewModelScope.launch {
            when (val result = repository.getForecastWeather(cityId, isRefresh)) {
                is Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
//                        if (result.data.size >= 7) {
//                            _weatherForecast.value = result.data.subList(0, 7)
//                        } else {
//                            _weatherForecast.value = result.data
//                        }
                        _weatherForecast.value = result.data
                        if (isRefresh) {
                            repository.deleteForecastData()
                            repository.storeForecastData(result.data)
                        }
                    } else {
                        _weatherForecast.postValue(listOf())
                    }
                }
                is Result.Error -> {
                    _isLoading.value = false
                }

                is Result.Loading -> {
                    _isLoading.postValue(false)
                }
            }
        }
    }
}
