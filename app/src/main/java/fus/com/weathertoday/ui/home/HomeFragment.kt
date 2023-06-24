package fus.com.weathertoday.ui.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fus.com.weathertoday.R
import fus.com.weathertoday.data.model.LocationModel
import fus.com.weathertoday.data.model.Weather
import fus.com.weathertoday.databinding.FragmentHomeBinding
import fus.com.weathertoday.ui.BaseFragment
import fus.com.weathertoday.ui.MainActivity
import fus.com.weathertoday.ui.container.ContainerFragmentDirections
import fus.com.weathertoday.ui.home.adapter.CityHoursWeatherAdapter
import fus.com.weathertoday.utils.Constants
import fus.com.weathertoday.utils.SharedPreferenceHelper
import fus.com.weathertoday.utils.Utils
import fus.com.weathertoday.utils.convertKelvinToCelsius
import fus.com.weathertoday.utils.observeOnce
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val LOCATION_REQUEST_CODE = 100
        private const val REQUEST_ENABLE_GPS = 1001
    }

    private lateinit var binding: FragmentHomeBinding
    private var isGPSEnabled = false

    @Inject
    lateinit var prefs: SharedPreferenceHelper
    private val adapter = CityHoursWeatherAdapter()
    private val viewModel by viewModels<HomeFragmentViewModel> { viewModelFactoryProvider }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.viewModel = viewModel
        observeViewModels()
        binding.swipeRefreshId.setOnRefreshListener {
            binding.progressBar.visibility = View.VISIBLE
            invokeLocationAction(true)
            binding.swipeRefreshId.isRefreshing = false
        }
        binding.viewContentDetail.rcvWeatherHoursInDay.adapter = adapter
        binding.viewContentDetail.rcvWeatherHoursInDay.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.viewContentDetail.llNextSevenDayContainer.setOnClickListener {
            val action = ContainerFragmentDirections.actionHomeFragmentToCitySevenDaysWeatherFragment()
            findNavController().navigate(action)
        }
//        activity?.window?.statusBarColor = resources.getColor(R.color.bg_item_blue_white, null)

        setVisibleAllView(false, isError = false)
        requestPermissionRationale()
    }

    private fun observeViewModels() {
        with(viewModel) {
            weather.observe(viewLifecycleOwner) { weather ->
                weather?.let {
                    prefs.saveCityId(it.cityId)
                    prefs.saveCityName("${it.name}, ${it.sys.country}")
                    updateDataWeatherToDayToView(it)
                    binding.invalidateAll()
                }
            }
            weatherForecast.observe(viewLifecycleOwner) { weatherForecast ->
                weatherForecast?.let {
                    adapter.submitList(it)
                    binding.invalidateAll()
                }
            }

            dataFetchState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> {
                        setVisibleAllView(true, isError = false)
                    }

                    false -> {
                        setVisibleAllView(false, isError = true)
                    }
                }
            }

            isLoading.observe(viewLifecycleOwner) { state ->
                when (state) {
                    true -> {
                        setVisibleAllView(false, isError = false)
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

    private fun invokeLocationAction(isRefresh: Boolean) {
        if (isGpsEnabled()) {
            if (allPermissionsGranted()) {
                viewModel.fetchLocationLiveData().observeOnce(
                    viewLifecycleOwner
                ) { location ->
                    if (isRefresh) viewModel.refreshWeather(location)
                    else viewModel.getWeather(location)
                }
            } else {
                viewModel.getWeather(LocationModel(105.780548, 21.028833))
            }
        } else {
            showDialogRequestGpsSetting()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale() = REQUIRED_PERMISSIONS.all {
        shouldShowRequestPermissionRationale(it)
    }

    private fun requestPermissionRationale() {
        if (allPermissionsGranted()) {
            invokeLocationAction(false)
            return
        }

        if (shouldShowRequestPermissionRationale()) {
            AlertDialog.Builder(requireContext())
                .setTitle("Truy cập vị trí hiện tại")
                .setMessage("Cho phép truy cập vị trí hiện tại của bạn")
                .setNegativeButton(
                    "Không cho phép"
                ) { _, _ ->
                    viewModel.getWeather(LocationModel(105.780548, 21.028833))
                }
                .setPositiveButton(
                    "Cho phép"
                ) { _, _ ->
                    requestPermissions(REQUIRED_PERMISSIONS, LOCATION_REQUEST_CODE)
                }
                .show()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, LOCATION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            invokeLocationAction(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ENABLE_GPS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        isGPSEnabled = true
                        invokeLocationAction(false)
                    }

                    Activity.RESULT_CANCELED -> {
                        Snackbar.make(
                            binding.root,
                            "Hãy bật GPS để lấy thông tin thời tiết tại vị trí của bạn",
                            Snackbar.LENGTH_LONG
                        ).show()
                        viewModel.getWeather(LocationModel(105.780548, 21.028833))
                    }
                }
            }
        }
    }

    private fun updateDataWeatherToDayToView(data: Weather) {
        val cityName = "${data.name}, ${data.sys.country}"
        binding.viewContentDetail.tvWeatherTodayCity.text = Utils.getCityName(
            cityName,
            Constants.SPAN_PROPORTION_1_4,
            resources.getColor(R.color.black, null)
        )
        binding.viewContentDetail.icWeatherTodayStatus.setImageResource(
            Utils.getWeatherStatusImg(
                data.networkWeatherDescription.firstOrNull()?.icon ?: ""
            )
        )
        binding.viewContentDetail.tvWeatherTodayStatus.text =
            Utils.capitalize(data.networkWeatherDescription.firstOrNull()?.description ?: "")
        binding.viewContentDetail.tvWeatherTodayDate.text = viewModel.currentSystemDate()
        val temp = Utils.formatTempValue(convertKelvinToCelsius(data.networkWeatherCondition.temp))
        binding.viewContentDetail.tvWeatherTodayTemperature.text = Utils.getTemperature(
            "$temp${Constants.TEMPERATURE_UNIT}",
            Constants.SPAN_PROPORTION_0_4,
            resources.getColor(R.color.white, null)
        )
        binding.viewContentDetail.tvWindValue.text = "${data.wind.speed} ${Constants.KmInHourUnit}"
        val feelsLike =
            Utils.formatTempValue(convertKelvinToCelsius(data.networkWeatherCondition.feelsLike))
        binding.viewContentDetail.tvFeelsLikeValue.text = Utils.getTemperature(
            "$feelsLike${Constants.TEMPERATURE_UNIT}",
            Constants.SPAN_PROPORTION_0_4,
            resources.getColor(R.color.white, null)
        )
        val pressure = Utils.formatTempValue(data.networkWeatherCondition.pressure)
        binding.viewContentDetail.tvPressureValue.text = "$pressure ${Constants.PressureUnit}"
        val humidity = Utils.formatTempValue(data.networkWeatherCondition.humidity)
        binding.viewContentDetail.tvHumidityValue.text = "$humidity ${Constants.PERCENT_UNIT}"
    }

    private fun setVisibleAllView(visible: Boolean, isError: Boolean) {
        binding.viewContentDetail.apply {
            if (visible) {
                tvWeatherTodayDate.visibility = View.VISIBLE
                clWeatherTodayDetail.visibility = View.VISIBLE
                tvToday.visibility = View.VISIBLE
                llNextSevenDayContainer.visibility = View.VISIBLE
                rcvWeatherHoursInDay.visibility = View.VISIBLE
            } else {
                tvWeatherTodayDate.visibility = View.GONE
                clWeatherTodayDetail.visibility = View.GONE
                tvToday.visibility = View.GONE
                llNextSevenDayContainer.visibility = View.GONE
                rcvWeatherHoursInDay.visibility = View.GONE
            }

            weatherTvErrorText.visibility = if (isError) View.VISIBLE else View.GONE
        }
    }

    private fun showDialogRequestGpsSetting() {
        AlertDialog.Builder(requireContext())
            .setTitle("Cho phép GPS")
            .setMessage("Cho phép GPS của bạn")
            .setNegativeButton(
                "Không cho phép"
            ) { _, _ ->
                viewModel.getWeather(LocationModel(105.780548, 21.028833))
            }
            .setPositiveButton(
                "Cho phép"
            ) { _, _ ->
                openGpsSettings()
            }
            .show()
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    }

    private fun openGpsSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivityForResult(intent, REQUEST_ENABLE_GPS)
    }

}