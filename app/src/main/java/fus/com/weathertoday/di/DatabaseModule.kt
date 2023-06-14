package fus.com.weathertoday.di

import android.content.Context
import androidx.room.Room
import fus.com.weathertoday.data.source.local.WeatherDatabase
import fus.com.weathertoday.data.source.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, "WeatherToday.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao
    }
}
