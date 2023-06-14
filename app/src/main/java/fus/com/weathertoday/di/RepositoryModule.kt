package fus.com.weathertoday.di

import fus.com.weathertoday.data.source.repository.WeatherRepository
import fus.com.weathertoday.data.source.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
