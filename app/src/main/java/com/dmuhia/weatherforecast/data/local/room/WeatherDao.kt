package com.dmuhia.weatherforecast.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dmuhia.weatherforecast.data.local.entities.CurrentWeatherEntity
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeather():CurrentWeatherEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(forecast: ForecastWeatherEntity)

    @Query("SELECT * FROM forecast_weather WHERE LOWER(cityName) = LOWER(:city)")
    suspend fun getWeatherForCity(city: String): ForecastWeatherEntity?
}

