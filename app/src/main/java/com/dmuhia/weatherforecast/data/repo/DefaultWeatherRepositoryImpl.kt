package com.dmuhia.weatherforecast.data.repo

import android.net.ConnectivityManager
import com.dmuhia.weatherforecast.data.local.LocalWeatherDataSource
import com.dmuhia.weatherforecast.data.remote.RemoteWeatherDataSource
import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.domain.DayForecastWeather
import com.dmuhia.weatherforecast.domain.DefaultWeatherRepository
import com.dmuhia.weatherforecast.domain.mappers.toCurrentWeather
import com.dmuhia.weatherforecast.domain.mappers.toCurrentWeatherEntity
import com.dmuhia.weatherforecast.domain.mappers.toDayForecastWeather
import com.dmuhia.weatherforecast.domain.mappers.toForecastWeatherEntity
import com.dmuhia.weatherforecast.utils.Resource
import com.dmuhia.weatherforecast.utils.resolveException
import timber.log.Timber
import javax.inject.Inject

class DefaultWeatherRepositoryImpl @Inject constructor(private val remoteDataSource:RemoteWeatherDataSource,
                                                       private val localDataSource:LocalWeatherDataSource,
                                                       private val connectivityManager: ConnectivityManager
): DefaultWeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Resource<CurrentWeather> {
        return try {
            if (connectivityManager.activeNetwork !=null){
                when(val remoteResponse = remoteDataSource.getWeather(lat, lon)){
                    is Resource.Success ->{
                        Timber.e("DefaultWeatherRepositoryImpl Success")
                        val data = remoteResponse.data
                        if (data != null) {
                            localDataSource.insertCurrentWeather(data.toCurrentWeatherEntity())
                        }
                        Resource.Success(data)
                    }
                    is Resource.Error ->{
                        Timber.e("DefaultWeatherRepositoryImpl ERROR: ${remoteResponse.message}")
                        Resource.Error(remoteResponse.message)
                    }
                }
            } else { //no network fetch from local
                val currentWeather = localDataSource.getCurrentWeather()
                if (currentWeather != null) {
                    Resource.Success(currentWeather.toCurrentWeather())
                }else{
                    Resource.Error("No data available. Please turn on the internet and try again.")
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resolveException(e))
        }

    }

    override suspend fun getWeatherForecast(cityName: String): Resource<DayForecastWeather> {
        return try {
            if (connectivityManager.activeNetwork !=null){
                when(val remoteResponse = remoteDataSource.getWeatherForecast(cityName)){
                    is Resource.Success ->{
                        Timber.e("DefaultWeatherRepositoryImpl getWeatherForecast Success")
                        val data = remoteResponse.data

                        if (data != null) {
                            localDataSource.insertForecastWeather(data.toForecastWeatherEntity())
                        }
                        Resource.Success(data)
                    }
                    is Resource.Error ->{
                        Timber.e("DefaultWeatherRepositoryImpl ERROR: ${remoteResponse.message}")
                        Resource.Error(remoteResponse.message)
                    }
                }
            } else { //no network fetch from local
                Timber.e("CITY NAME:: $cityName")
                val currentWeather = localDataSource.getWeatherForCity(cityName)
                if (currentWeather != null) {
                    Resource.Success(currentWeather.toDayForecastWeather())
                }else{
                    Resource.Error("No data available. Please turn on the internet and try again.")
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resolveException(e))
        }
    }
}