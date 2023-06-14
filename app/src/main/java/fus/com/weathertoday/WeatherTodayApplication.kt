package fus.com.weathertoday

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import fus.com.weathertoday.data.source.repository.WeatherRepository
import fus.com.weathertoday.utils.ThemeManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class WeatherTodayApplication : Application()
//    , Configuration.Provider
{

    companion object {
        var instance: WeatherTodayApplication? = null
        fun context() = instance!!.applicationContext!!
    }

    @Inject
    lateinit var weatherRepository: WeatherRepository

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
        initTheme()
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        runCatching {
            ThemeManager.applyTheme(requireNotNull(preferences.getString("theme_key", "")))
        }.onFailure { exception ->
            Timber.e("Theme Manager: $exception")
        }
    }

//    override fun getWorkManagerConfiguration(): Configuration {
//        val myWorkerFactory = DelegatingWorkerFactory()
//        myWorkerFactory.addFactory(MyWorkerFactory(weatherRepository))
        // Add here other factories that you may need in this application

//        return Configuration.Builder()
//            .setMinimumLoggingLevel(Log.INFO)
//            .setWorkerFactory(myWorkerFactory)
//            .build()
//    }
}
