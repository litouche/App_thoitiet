package fus.com.weathertoday.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.universalcalendar.extensions.DiffCallBack
import com.example.universalcalendar.extensions.OnClickItem
import fus.com.weathertoday.R
import fus.com.weathertoday.data.model.WeatherForecast
import fus.com.weathertoday.databinding.ItemWeatherHourInDayDetailBinding
import fus.com.weathertoday.utils.Constants
import fus.com.weathertoday.utils.DateUtils
import fus.com.weathertoday.utils.Utils
import fus.com.weathertoday.utils.convertKelvinToCelsius
import java.util.*

class CityHoursWeatherAdapter :
    ListAdapter<WeatherForecast, CityHoursWeatherAdapter.CityHoursWeatherViewHolder>(
        DiffCallBack<WeatherForecast>()
    ) {
    var listener: OnClickItem<WeatherForecast>? = null

    inner class CityHoursWeatherViewHolder(private val viewBinding: ItemWeatherHourInDayDetailBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(dto: WeatherForecast) {
            itemView.run {
                viewBinding.tvHourInDay.text = DateUtils.convertDateToString(
                    DateUtils.convertStringToDate(DateUtils.DATE_WEATHER_FORECAST_FORMAT, dto.date),
                    DateUtils.HOUR_MINUTE_FORMAT
                )
                viewBinding.icWeatherHourInDayStatus.setImageResource(
                    Utils.getWeatherStatusImg(
                        dto.networkWeatherDescription.firstOrNull()?.icon ?: ""
                    )
                )
                val temp =
                    Utils.formatTempValue(convertKelvinToCelsius(dto.networkWeatherCondition.temp))
                viewBinding.executePendingBindings()
                setOnClickListener {
                    listener?.onClick(dto)
                }

                val currentDate = Date(System.currentTimeMillis())
                val dtoDate = DateUtils.convertStringToDate("yyyy-MM-dd", dto.date)

                if (currentDate.day == dtoDate?.day) {
                    viewBinding.clHourInDayContainer.background =
                        resources.getDrawable(R.drawable.bg_item_weather_today_blue_item)
                    viewBinding.tvHourInDay.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                    viewBinding.tvWeatherHourInDayTemperature.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                    viewBinding.tvWeatherHourInDayTemperature.text = Utils.getTemperature(
                        "$temp${Constants.TEMPERATURE_UNIT}",
                        Constants.SPAN_PROPORTION_0_4,
                        resources.getColor(R.color.white, null)
                    )
                } else {
                    viewBinding.clHourInDayContainer.background =
                        resources.getDrawable(R.drawable.bg_item_weather_today_white)
                    viewBinding.tvHourInDay.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.gray
                        )
                    )
                    viewBinding.tvWeatherHourInDayTemperature.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.darkgray
                        )
                    )
                    viewBinding.tvWeatherHourInDayTemperature.text = Utils.getTemperature(
                        "$temp${Constants.TEMPERATURE_UNIT}",
                        Constants.SPAN_PROPORTION_0_4,
                        resources.getColor(R.color.darkgray, null)
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHoursWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherHourInDayDetailBinding.inflate(inflater, parent, false)
        return CityHoursWeatherViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CityHoursWeatherViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}