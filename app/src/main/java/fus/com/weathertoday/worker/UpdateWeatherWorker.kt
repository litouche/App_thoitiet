package fus.com.weathertoday.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import fus.com.weathertoday.data.source.repository.WeatherRepository
import fus.com.weathertoday.utils.NotificationHelper
import fus.com.weathertoday.utils.Result
import fus.com.weathertoday.utils.SharedPreferenceHelper

class UpdateWeatherWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: WeatherRepository
) : CoroutineWorker(context, params) {
    private val notificationHelper = NotificationHelper("Weather Update", context)
    private val sharedPrefs = SharedPreferenceHelper.getInstance(context)

    override suspend fun doWork(): Result {
        val location = sharedPrefs.getLocation()
        return when (val result = repository.getWeather(location, true)) {
            is fus.com.weathertoday.utils.Result.Success -> {
                if (result.data != null) {
                    when (
                        val foreResult =
                            repository.getForecastWeather(result.data.cityId, true)
                    ) {
                        is fus.com.weathertoday.utils.Result.Success -> {
                            if (foreResult.data != null) {
                                notificationHelper.createNotification()
                                Result.success()
                            } else {
                                Result.failure()
                            }
                        }
                        else -> Result.failure()
                    }
                } else {
                    Result.failure()
                }
            }
            else -> Result.failure()
        }
    }
}
