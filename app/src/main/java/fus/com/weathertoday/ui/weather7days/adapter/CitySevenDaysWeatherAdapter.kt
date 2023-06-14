package fus.com.weathertoday.ui.weather7days.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.universalcalendar.extensions.DiffCallBack
import com.example.universalcalendar.extensions.OnClickItem
import fus.com.weathertoday.R
import fus.com.weathertoday.data.model.WeatherForecastCustom
import fus.com.weathertoday.databinding.ItemWeatherSevenDayBinding
import fus.com.weathertoday.utils.Constants
import fus.com.weathertoday.utils.DateUtils
import fus.com.weathertoday.utils.Utils
import fus.com.weathertoday.utils.convertKelvinToCelsius

class CitySevenDaysWeatherAdapter :
    ListAdapter<WeatherForecastCustom, CitySevenDaysWeatherAdapter.CitySevenDaysWeatherViewHolder>(
        DiffCallBack<WeatherForecastCustom>()
    ) {
    var listener: OnClickItem<WeatherForecastCustom>? = null

    inner class CitySevenDaysWeatherViewHolder(private val viewBinding: ItemWeatherSevenDayBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindData(dto: WeatherForecastCustom) {
            itemView.run {
                viewBinding.executePendingBindings()
                val temp =
                    Utils.formatTempValue(convertKelvinToCelsius(dto.maxTemp)) + " ~ " +
                            Utils.formatTempValue(convertKelvinToCelsius(dto.minTemp))
                viewBinding.tvWeatherSevenDaysTemp.text = Utils.getTemperature(
                    "$temp${Constants.TEMPERATURE_UNIT}",
                    Constants.SPAN_PROPORTION_0_4,
                    resources.getColor(R.color.white, null)
                )

                viewBinding.tvWeatherSevenDaysDate.text = DateUtils.convertDateToString(
                    DateUtils.convertStringToDate(DateUtils.DATE_WEATHER_FORECAST_FORMAT, dto.date),
                    DateUtils.DAY_MONTH
                )

                viewBinding.icWeatherSevenDaysStatus.setImageResource(
                    Utils.getWeatherStatusImg(dto.icon)
                )
                setOnClickListener {
                    listener?.onClick(dto)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitySevenDaysWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherSevenDayBinding.inflate(inflater, parent, false)
        return CitySevenDaysWeatherViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CitySevenDaysWeatherViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}