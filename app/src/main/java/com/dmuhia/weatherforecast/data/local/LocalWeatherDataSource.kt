package com.dmuhia.weatherforecast.data.local

import com.dmuhia.weatherforecast.data.local.entities.CurrentWeatherEntity
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherEntity

interface LocalWeatherDataSource {
    suspend fun insertCurrentWeather(weather:CurrentWeatherEntity)
    suspend fun getCurrentWeather():CurrentWeatherEntity?

    suspend fun insertForecastWeather(forecast: ForecastWeatherEntity)
    suspend fun getWeatherForCity(city: String): ForecastWeatherEntity?



}