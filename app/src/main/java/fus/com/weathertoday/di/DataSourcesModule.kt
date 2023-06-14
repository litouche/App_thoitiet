package fus.com.weathertoday.di

import fus.com.weathertoday.data.source.local.WeatherLocalDataSource
import fus.com.weathertoday.data.source.local.WeatherLocalDataSourceImpl
import fus.com.weathertoday.data.source.remote.WeatherRemoteDataSource
import fus.com.weathertoday.data.source.remote.WeatherRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourcesModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource
}
