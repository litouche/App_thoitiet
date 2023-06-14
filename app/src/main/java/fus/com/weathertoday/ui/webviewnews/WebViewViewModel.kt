package fus.com.weathertoday.ui.webviewnews

import androidx.lifecycle.ViewModel
import fus.com.weathertoday.data.source.repository.WeatherRepository
import javax.inject.Inject

class WebViewViewModel @Inject constructor(
    private val repository: WeatherRepository
) :
    ViewModel() {

}
