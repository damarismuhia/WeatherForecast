package com.dmuhia.weatherforecast.data.model

data class ForecastDayData(
    var isFromLocal: Boolean = false,
    val date: String,
    val dayCondition: String,
    val dayIcon: String,
    val maxTempC: Double,
    val minTempC: Double,
    val maxTempF: Double,
    val minTempF: Double,
    val dayAverageWindSpeed: Double,
    val humidity: Int,
    val hour: List<ForecastHourlyWeather>
)
data class ForecastHourlyWeather(
    val temperatureC: Double,
    val temperatureF: Double,
    val time: String,
    val icon: String,
)