package fus.com.weathertoday.widgets

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomAlignTopSpannableString: MetricAffectingSpan() {

    var ratio = 0.5

    override fun updateDrawState(paint: TextPaint) {
        paint.baselineShift += (paint.ascent() * ratio).toInt()
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.baselineShift += (paint.ascent() * ratio).toInt()
    }
}