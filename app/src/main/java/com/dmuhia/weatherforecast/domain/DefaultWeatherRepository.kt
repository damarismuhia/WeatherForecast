package com.dmuhia.weatherforecast.domain

import com.dmuhia.weatherforecast.utils.Resource

interface DefaultWeatherRepository {
    suspend fun getWeather(lat:Double,lon:Double): Resource<CurrentWeather>

 suspend fun getWeatherForecast(cityName: String): Resource<DayForecastWeather>
}