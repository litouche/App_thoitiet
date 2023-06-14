package fus.com.weathertoday.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import fus.com.weathertoday.R
import fus.com.weathertoday.widgets.CustomAlignTopSpannableString

object Utils {

    fun getSpannableStringForTextView(
        text: String,
        proportion: Float,
        startIndex: Int,
        endIndex: Int,
        flag: Int,
        colorId: Int
    ): SpannableString {
        val span = SpannableString(text)
        span.setSpan(
            RelativeSizeSpan(proportion), startIndex, endIndex,
            flag
        )
        span.setSpan(
            ForegroundColorSpan(colorId), startIndex, endIndex,
            flag
        )
        return span
    }

    fun getTopSpannableStringForTextView(
        text: String,
        proportion: Float,
        startIndex: Int,
        endIndex: Int,
        flag: Int,
        colorId: Int,
        topRatio: Double
    ): SpannableString {
        val span = SpannableString(text)
        span.setSpan(
            RelativeSizeSpan(proportion), startIndex, endIndex,
            flag
        )
        span.setSpan(
            ForegroundColorSpan(colorId), startIndex, endIndex,
            flag
        )
        span.setSpan(
            CustomAlignTopSpannableString().apply { ratio = topRatio }, startIndex, endIndex,
            flag
        )
        return span
    }

    fun getCityName(name: String, proportion: Float, colorId: Int): SpannableString {
        val indexComma = name.indexOf(Constants.COMMA)
        return getSpannableStringForTextView(
            name,
            proportion,
            0,
            indexComma,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
            colorId
        )
    }

    fun getTemperature(name: String, proportion: Float, colorId: Int): SpannableString {
        return getTopSpannableStringForTextView(
            name,
            proportion,
            name.length - 1,
            name.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE,
            colorId,
            Constants.SPAN_TOP_RATIO_1_4
        )
    }

    fun capitalize(str: String): String {
        return str.trim().split("\\s+".toRegex())
            .joinToString(" ") { it.capitalize() }
    }

    fun formatTempValue(value: Double): String {
        return String.format("%.0f", value)
    }

    fun getWeatherStatusImg(icon: String): Int {
        return Constants.mapWeatherStatusIcon[icon] ?: R.drawable.ic_few_cloud
    }

}