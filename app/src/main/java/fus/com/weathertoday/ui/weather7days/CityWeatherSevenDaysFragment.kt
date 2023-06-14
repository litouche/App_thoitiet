package fus.com.weathertoday.ui.weather7days

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.databinding.FragmentWeatherNext7DayBinding
import fus.com.weathertoday.ui.BaseFragment
import fus.com.weathertoday.ui.MainActivity
import fus.com.weathertoday.ui.weather7days.adapter.CitySevenDaysWeatherAdapter
import fus.com.weathertoday.utils.Constants
import fus.com.weathertoday.utils.SharedPreferenceHelper
import fus.com.weathertoday.utils.Utils
import javax.inject.Inject

@AndroidEntryPoint
class CityWeatherSevenDaysFragment : BaseFragment() {

    private lateinit var binding: FragmentWeatherNext7DayBinding

    @Inject
    lateinit var prefs: SharedPreferenceHelper

    private var cityId = 0
    private val adapter = CitySevenDaysWeatherAdapter()
    private val viewModel by viewModels<CityWeatherSevenDaysViewModel> { viewModelFactoryProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityId = prefs.getCityId() ?: 0

    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction(false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherNext7DayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
        binding.swipeRefreshId.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            invokeLocationAction(true)
            binding.swipeRefreshId.isRefreshing = false
        }
        binding.rcvWeatherSevenDays.adapter = adapter
        binding.rcvWeatherSevenDays.layoutManager = LinearLayoutManager(context)
        binding.icBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        val cityName = prefs.getCityName() ?: ""
        binding.tvCityName.text = Utils.getCityName(
            cityName,
            Constants.SPAN_PROPORTION_1_4,
            resources.getColor(R.color.white, null)
        )
//        activity?.window?.statusBarColor = resources.getColor(R.color.bg_item_blue, null)
    }

    private fun invokeLocationAction(isRefresh: Boolean) {
        viewModel.getForecastWeather(cityId, isRefresh)
    }

    private fun observeViewModels() {
        with(viewModel) {
            weather.observe(viewLifecycleOwner) { weather ->
                weather?.let {
//                    adapter.submitList(it)
                }
            }

            weatherCustom.observe(viewLifecycleOwner) { weather ->
                weather?.let {
                    adapter.submitList(it)
                }
            }

            dataFetchState.observe(viewLifecycleOwner) {
                //do nothing
            }

            isLoading.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> {
                        binding.apply {
                            progressBar.visibility = View.VISIBLE
                        }
                    }
                    false -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

}