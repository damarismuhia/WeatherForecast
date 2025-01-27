package com.dmuhia.weatherforecast.data.remote

import com.dmuhia.weatherforecast.data.model.ForecastResponse
import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.domain.DayForecastWeather
import com.dmuhia.weatherforecast.utils.Resource

interface RemoteWeatherDataSource {
    suspend fun getWeather(lat:Double,lon:Double): Resource<CurrentWeather>

    suspend fun getWeatherForecast(cityName: String): Resource<DayForecastWeather>

}