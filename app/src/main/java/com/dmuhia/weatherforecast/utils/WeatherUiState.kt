package com.dmuhia.weatherforecast.utils

import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.data.model.ForecastDayData
import com.dmuhia.weatherforecast.domain.DayForecastWeather

data class CurrentWeatherUiState(
    val weather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
data class ForecastWeatherUiState(
    val forecast: DayForecastWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
