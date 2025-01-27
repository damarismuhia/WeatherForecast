package com.dmuhia.weatherforecast.data.remote

import com.dmuhia.weatherforecast.data.model.ForecastResponse
import com.dmuhia.weatherforecast.data.model.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    //Get the weather information for the user's location
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unit: String = "metric",
    ): Response<CurrentWeatherResponse>

    //Get the five days weather information for a given city
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") cityName: String,
        @Query("cnt") days: Int = 5,
        @Query("units") unit: String = "metric"
    ): Response<ForecastResponse>

}
