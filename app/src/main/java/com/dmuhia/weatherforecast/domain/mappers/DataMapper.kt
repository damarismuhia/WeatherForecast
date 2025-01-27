package com.dmuhia.weatherforecast.domain.mappers

import com.dmuhia.weatherforecast.data.local.entities.CurrentWeatherEntity
import com.dmuhia.weatherforecast.data.local.entities.ForecastWeatherEntity
import com.dmuhia.weatherforecast.data.model.CurrentWeatherResponse
import com.dmuhia.weatherforecast.data.model.ForecastResponse
import com.dmuhia.weatherforecast.domain.CurrentWeather
import com.dmuhia.weatherforecast.domain.DayForecastWeather
import com.dmuhia.weatherforecast.domain.ForecastWeather
import com.dmuhia.weatherforecast.utils.formatUnixToDate

fun CurrentWeatherResponse.toCurrentWeather(): CurrentWeather{
    return CurrentWeather(
        cityName =name, 
        visibility = visibility,
        condition = weather.first().description,
        icon = weather.first().icon,
        temp = main.temp,
        maxTemp =main.tempMax,
        minTemp = main.tempMin,
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        dt = formatUnixToDate(dt,timezone,"EEE, dd MMM yyyy | hh:mm a"),
    )
    
}
fun CurrentWeatherEntity.toCurrentWeather(): CurrentWeather{
    return CurrentWeather(
        cityName = cityName,
        visibility = visibility,
        condition = condition,
        icon = icon,
        temp = temp,
        maxTemp =maxTemp,
        minTemp = minTemp,
        humidity = humidity,
        pressure = pressure,
        windSpeed = windSpeed,
        dt = dt,
    )

}
fun CurrentWeather.toCurrentWeatherEntity(): CurrentWeatherEntity{
    return CurrentWeatherEntity(
        cityName = cityName,
        visibility = visibility,
        condition = condition,
        icon = icon,
        temp = temp,
        maxTemp =maxTemp,
        minTemp = minTemp,
        humidity = humidity,
        pressure = pressure,
        windSpeed = windSpeed,
        dt = dt,
    )

}
// Forecast Weather
fun ForecastResponse.toDayForecastWeather(): DayForecastWeather{
    return DayForecastWeather(
        cityName = city.name,
        forecastWeatherDetails = list.map { wd->
            ForecastWeather (
                visibility = wd.visibility,
                condition = wd.weather.firstOrNull()?.description ?: "",
                icon = wd.weather.firstOrNull()?.icon ?: "",
                temp = wd.main.temp,
                maxTemp = wd.main.tempMax,
                minTemp = wd.main.tempMin,
                humidity = wd.main.humidity,
                pressure = wd.main.pressure,
                windSpeed = wd.wind.speed,
                dt = formatUnixToDate(wd.dt, timeZone = city.timezone,"EEE, dd MMM"),
            )

        }
    )


}
fun DayForecastWeather.toForecastWeatherEntity(): ForecastWeatherEntity {
    return ForecastWeatherEntity (
        cityName = cityName,
        weather = forecastWeatherDetails.map { data->
            ForecastWeather(
                visibility = data.visibility,
                condition = data.condition,
                icon = data.icon,
                temp = data.temp,
                maxTemp = data.maxTemp,
                minTemp = data.minTemp,
                humidity = data.humidity,
                pressure = data.pressure,
                windSpeed = data.windSpeed,
                dt = data.dt,
            )
        }
    )

}
fun ForecastWeatherEntity.toDayForecastWeather(): DayForecastWeather {
    return DayForecastWeather (
        cityName = cityName,
        forecastWeatherDetails = weather.map { data->
            ForecastWeather(
                visibility = data.visibility,
                condition = data.condition,
                icon = data.icon,
                temp = data.temp,
                maxTemp = data.maxTemp,
                minTemp = data.minTemp,
                humidity = data.humidity,
                pressure = data.pressure,
                windSpeed = data.windSpeed,
                dt = data.dt,
            )


        }
    )

}
