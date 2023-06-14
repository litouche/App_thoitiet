package fus.com.weathertoday.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import fus.com.weathertoday.data.model.LocationModel
import com.google.gson.Gson

class SharedPreferenceHelper {
    companion object {

        private const val WEATHER_PREF_TIME = "Weather pref time"
        private const val WEATHER_FORECAST_PREF_TIME = "Forecast pref time"
        private const val CITY_ID = "City ID"
        private const val CITY_NAME = "City Name"
        private var prefs: SharedPreferences? = null
        private const val LOCATION = "LOCATION"

        @Volatile
        private var instance: SharedPreferenceHelper? = null

        /**
         * This checks if there is an existing instance of the [SharedPreferences] in the
         * specified [context] and creates one if there isn't or else, it returns the
         * already existing instance. This function ensures that the [SharedPreferences] is
         * accessed at any instance by a single thread.
         */
        fun getInstance(context: Context): SharedPreferenceHelper {
            synchronized(this) {
                val _instance = instance
                if (_instance == null) {
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    instance = _instance
                }
                return SharedPreferenceHelper()
            }
        }
    }

    /**
     * This function saves the initial time [System.nanoTime] at which the weather information
     * at the user's location is accessed.
     * @param time the value of [System.nanoTime] when the weather information is received.
     */
    fun saveTimeOfInitialWeatherFetch(time: Long) {
        prefs?.edit(commit = true) {
            putLong(WEATHER_PREF_TIME, time)
        }
    }

    /**
     * This function returns the saved value of [System.nanoTime] when the weather information
     * at the user's location was accessed.
     * @see saveTimeOfInitialWeatherFetch
     */
    fun getTimeOfInitialWeatherFetch() = prefs?.getLong(WEATHER_PREF_TIME, 0L)

    /**
     * This function saves the initial time [System.nanoTime] at which the weather forecast
     * at the user's location is accessed.
     * @param time the value of [System.nanoTime] when the weather forecast is received.
     */
    fun saveTimeOfInitialWeatherForecastFetch(time: Long) {
        prefs?.edit(commit = true) {
            putLong(WEATHER_FORECAST_PREF_TIME, time)
        }
    }

    /**
     * This function returns the saved value of [System.nanoTime] when the weather forecast
     * at the user's location was accessed.
     * @see saveTimeOfInitialWeatherForecastFetch
     */
    fun getTimeOfInitialWeatherForecastFetch() = prefs?.getLong(WEATHER_FORECAST_PREF_TIME, 0L)

    /**
     * This function saves the [cityId] of the location whose weather information has been
     * received.
     * @param cityId the id of the location whose weather has been received
     */
    fun saveCityId(cityId: Int) {
        prefs?.edit(commit = true) {
            putInt(CITY_ID, cityId)
        }
    }

    /**
     * This function returns the id of the location whose weather information has been received.
     * @see saveCityId
     */
    fun getCityId() = prefs?.getInt(CITY_ID, 0)

    /**
     * This function saves the [cityName] of the location whose weather information has been
     * received.
     * @param name the Name of the location whose weather has been received
     */
    fun saveCityName(name: String) {
        prefs?.edit(commit = true) {
            putString(CITY_NAME, name)
        }
    }

    /**
     * This function returns the name of the location whose weather information has been received.
     * @see saveCityName
     */
    fun getCityName() = prefs?.getString(CITY_NAME, "")

    /**
     * This function gets the value of the cache duration the user set in the
     * Settings Fragment.
     */
    fun getUserSetCacheDuration() = prefs?.getString("cache_key", "0")

    /**
     * This function gets the value of the app theme the user set in the
     * Settings Fragment.
     */
    fun getSelectedThemePref() = prefs?.getString("theme_key", "")

    /**
     * This function gets the value of the temperature unit the user set in the
     * Settings Fragment.
     */
    fun getSelectedTemperatureUnit() = prefs?.getString("unit_key", "")

    /**
     * This function saves a [LocationModel]
     */
    fun saveLocation(location: LocationModel) {
        prefs?.edit(commit = true) {
            val gson = Gson()
            val json = gson.toJson(location)
            putString(LOCATION, json)
        }
    }

    /**
     * This function gets the value of the saved [LocationModel]
     */
    fun getLocation(): LocationModel {
        val gson = Gson()
        val json = prefs?.getString(LOCATION, null)
        return gson.fromJson(json, LocationModel::class.java)
    }


    fun getInt(key: String, defaultValue: Int = 0): Int {
        return prefs?.getInt(key, defaultValue) ?: defaultValue
    }

    fun put(key: String, value: Any) {
        val editor: SharedPreferences.Editor? = prefs?.edit()
        when (value) {
            is String -> editor?.putString(key, value)
            is Int -> editor?.putInt(key, value)
            is Boolean -> editor?.putBoolean(key, value)
            is Float -> editor?.putFloat(key, value)
            is Long -> editor?.putLong(key, value)
            else -> editor?.putString(key, value.toString())
        }
        editor?.apply()
    }

}
