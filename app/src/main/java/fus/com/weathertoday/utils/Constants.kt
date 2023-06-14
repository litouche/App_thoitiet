package fus.com.weathertoday.utils

import fus.com.weathertoday.R

object Constants {
    const val GPS_REQUEST_CHECK_SETTINGS = 102
    const val SPAN_PROPORTION_1_4 = 1.4f
    const val SPAN_PROPORTION_0_4 = 0.4f
    const val SPAN_TOP_RATIO_1_4 = 1.4
    const val TEMPERATURE_UNIT = "o"
    const val COMMA = ","
    const val KmInHourUnit = "Km/h"
    const val PressureUnit = "mbar"
    const val PERCENT_UNIT = "%"
    const val KEY_DEFAULT_COIN = "key_default_coin"

    val mapWeatherStatusIcon = hashMapOf(
        "01d" to R.drawable.ic_clear_sky,
        "01n" to R.drawable.ic_clear_sky,
        "02d" to R.drawable.ic_few_cloud,
        "02n" to R.drawable.ic_few_cloud,
        "03d" to R.drawable.ic_scattered_cloud,
        "03n" to R.drawable.ic_scattered_cloud,
        "04d" to R.drawable.ic_overcast_cloud,
        "04n" to R.drawable.ic_overcast_cloud,
        "50d" to R.drawable.ic_mist,
        "13d" to R.drawable.ic_snow,
        "10d" to R.drawable.ic_heavy_rain,
        "09d" to R.drawable.ic_heavy_rain,
        "11d" to R.drawable.ic_thunderstorm_rain,
    )
    const val KEY_COIN = "KEY_COIN" // 5 coin


    const val KEY_10_COIN = "key_100" // 100 coin

    const val KEY_20_COIN = "key_150" // 150 coin

    const val KEY_50_COIN = "key_300" // 300 coin

    const val KEY_100_COIN = "key_500" // 500 coin

    const val KEY_150_COIN = "key_700" // 700 coin

    const val KEY_200_COIN = "key_999" // 999 coin

}