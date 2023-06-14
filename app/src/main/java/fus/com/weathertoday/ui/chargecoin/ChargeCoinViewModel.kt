package fus.com.weathertoday.ui.chargecoin

import androidx.lifecycle.ViewModel
import fus.com.weathertoday.data.source.repository.WeatherRepository
import javax.inject.Inject

class ChargeCoinViewModel @Inject constructor(
    private val repository: WeatherRepository
) :
    ViewModel() {

}
