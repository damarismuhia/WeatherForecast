package com.dmuhia.weatherforecast.data.local

import com.dmuhia.weatherforecast.data.local.entities.CurrentWeatherEntity
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherEntity
import com.dmuhia.weatherforecast.data.local.room.WeatherDao
import com.dmuhia.weatherforecast.data.model.CurrentWeatherResponse
import com.dmuhia.weatherforecast.data.model.ForecastResponse
import com.dmuhia.weatherforecast.utils.Resource
import javax.inject.Inject

class LocalWeatherDataSourceImpl @Inject constructor(
    private val weatherDao:WeatherDao) : LocalWeatherDataSource {

    override suspend fun insertCurrentWeather(weather: CurrentWeatherEntity) {
        weatherDao.insertCurrentWeather(weather)
    }

    override suspend fun getCurrentWeather(): CurrentWeatherEntity {
        return weatherDao.getCurrentWeather()
    }

    override suspend fun insertForecastWeather(forecast: ForecastWeatherEntity) {
        weatherDao.insertForecastWeather(forecast)
    }

    override suspend fun getWeatherForCity(city: String): ForecastWeatherEntity? {
        return weatherDao.getWeatherForCity(city)
    }

}
