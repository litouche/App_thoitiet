@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package fus.com.weathertoday.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    const val DATE_WEATHER_FORECAST_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val HOUR_MINUTE_FORMAT = "d/M\nHH:mm"
    const val DAY_MONTH = "EEEE, dd 'tháng' MM"

    fun convertStringToDate(format: String, dateStr: String?): Date? {
        if (dateStr.isNullOrEmpty())
            return null
        return try {
            SimpleDateFormat(format, Locale("vi", "VN")).parse(dateStr)
        } catch (e: ParseException) {
            null
        }
    }

    fun convertDateToString(date: Date?, format: String): String? {
        if (date == null) return null
        return try {
            SimpleDateFormat(format, Locale("vi", "VN")).format(date)
        } catch (e: ParseException) {
            null
        }
    }
}