package com.dmuhia.weatherforecast.data.remote

import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.domain.DayForecastWeather
import com.dmuhia.weatherforecast.domain.mappers.toCurrentWeather
import com.dmuhia.weatherforecast.domain.mappers.toDayForecastWeather
import com.dmuhia.weatherforecast.utils.Resource
import com.dmuhia.weatherforecast.utils.resolveException
import timber.log.Timber
import javax.inject.Inject

class RemoteWeatherDataSourceImpl @Inject constructor(
    private val apiService: WeatherApiService) : RemoteWeatherDataSource {

    override suspend fun getWeather(lat: Double, lon: Double): Resource<CurrentWeather> {
        return try {
            val result = apiService.getCurrentWeather(latitude = lat, longitude = lon)
            if (result.isSuccessful && result.body() != null) {
                val currentWeatherResponse = result.body()
                Timber.e("Remote REPO SUCCESS: ${result.message()}")
                Resource.Success(currentWeatherResponse?.toCurrentWeather())
            } else {
                Timber.e("Remote REPO: ${result.message()}")
                Resource.Error(result.message())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            Resource.Error(resolveException(exception))
        }
    }

    override suspend fun getWeatherForecast(cityName: String): Resource<DayForecastWeather> {
        return try {
            val result = apiService.getWeatherForecast(cityName)
            if (result.isSuccessful && result.body() != null) {
                val forecastResponse = result.body()
                Resource.Success(forecastResponse?.toDayForecastWeather())
            } else {
                Resource.Error(result.message())
            }
        } catch (exception: Exception) {
            Resource.Error(resolveException(exception))
        }
    }
}
