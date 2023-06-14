package fus.com.weathertoday.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import fus.com.weathertoday.ui.ViewModelFactory
import fus.com.weathertoday.ui.home.HomeFragmentViewModel
import fus.com.weathertoday.ui.weather7days.CityWeatherSevenDaysViewModel


@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun bindHomeFragmentViewModel(viewModel: HomeFragmentViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(CityWeatherSevenDaysViewModel::class)
    abstract fun bindCityWeatherSevenDaysViewModel(viewModel: CityWeatherSevenDaysViewModel): ViewModel

}
