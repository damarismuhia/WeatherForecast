package com.dmuhia.weatherforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dmuhia.weatherforecast.data.local.entities.CurrentWeatherEntity
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherConverter
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherEntity
import com.dmuhia.weatherforecast.data.local.room.WeatherDao

@Database(entities = [CurrentWeatherEntity::class, ForecastWeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(ForecastWeatherConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}



