package com.dmuhia.weatherforecast.domain

data class DayForecastWeather (
    val cityName: String,
    val forecastWeatherDetails: List<ForecastWeather>,
)
data class ForecastWeather(
    val visibility: Int,
    val condition: String,
    val icon: String,
    val temp:Double,
    val maxTemp:Double,
    val minTemp:Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val dt: String,
)
