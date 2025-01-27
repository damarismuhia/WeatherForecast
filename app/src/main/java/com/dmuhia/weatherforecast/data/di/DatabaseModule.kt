package com.dmuhia.weatherforecast.data.di

import android.content.Context
import androidx.room.Room
import com.dmuhia.weatherforecast.data.local.AppDatabase
import com.dmuhia.weatherforecast.data.local.room.WeatherDao
import com.dmuhia.weatherforecast.utils.KeystoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        // Generate the passphrase for SQLCipher
        val key = KeystoreManager().generateKey()
       // val factory = SupportFactory(key.encoded)
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_forecast_database"
        )
           // .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}

