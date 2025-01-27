package com.dmuhia.weatherforecast.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.domain.DayForecastWeather
import com.dmuhia.weatherforecast.domain.DefaultWeatherRepository
import com.dmuhia.weatherforecast.domain.LocationModel
import com.dmuhia.weatherforecast.utils.CurrentWeatherUiState
import com.dmuhia.weatherforecast.utils.ForecastWeatherUiState
import com.dmuhia.weatherforecast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
                        private val defaultRepository: DefaultWeatherRepository): ViewModel() {

    private val _location = MutableStateFlow<LocationModel?>(null)
    val location: StateFlow<LocationModel?> = _location

    fun updateLocation(latitude: Double, longitude: Double) {
        _location.value = LocationModel(latitude, longitude)
    }

    private val _currentWeatherDetails = MutableStateFlow(CurrentWeatherUiState())
    val currentWeatherDetails: StateFlow<CurrentWeatherUiState> = _currentWeatherDetails.asStateFlow() //immutable



    private val _forecastUIState = MutableStateFlow(ForecastWeatherUiState())
    val forecastUIState: StateFlow<ForecastWeatherUiState> = _forecastUIState.asStateFlow() //immutable


    fun fetchCurrentWeatherData(lat:Double,log:Double) {
        viewModelScope.launch {
            _currentWeatherDetails.emit(CurrentWeatherUiState(isLoading = true))
           val result = defaultRepository.getWeather(lat,log)
            _currentWeatherDetails.emit(processCurrentWeatherResult(result))
        }
    }
    fun fetchWeatherForCity(city: String) {
        viewModelScope.launch {
            _forecastUIState.emit(ForecastWeatherUiState(isLoading = true))
           val result = defaultRepository.getWeatherForecast(city)
            _forecastUIState.emit(processHourlyWeatherResult(result))
        }
    }

    private fun processHourlyWeatherResult(result: Resource<DayForecastWeather>): ForecastWeatherUiState {
        return when (result) {
            is Resource.Success -> {
                ForecastWeatherUiState(
                    result.data,false,null
                )
            }
            is Resource.Error -> {
                ForecastWeatherUiState(
                    null,false,result.message
                )
            }
        }
    }
    private fun processCurrentWeatherResult(result: Resource<CurrentWeather>): CurrentWeatherUiState {
        return when (result) {
            is Resource.Success -> {
                CurrentWeatherUiState(
                    result.data,false,null
                )
            }
            is Resource.Error -> {
                Timber.e("ERROR MESSAGE IS ${result.message}")
                CurrentWeatherUiState(
                    null,false,result.message
                )
            }
        }
    }


}