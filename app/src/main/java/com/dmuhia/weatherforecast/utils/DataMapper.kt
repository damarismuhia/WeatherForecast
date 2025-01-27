package com.dmuhia.weatherforecast.utils

import com.dmuhia.weatherforecast.data.remote.NoNetworkException
import okhttp3.internal.http2.ConnectionShutdownException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.math.roundToInt

fun formatTemperatureValue(temperature: Double, unit: String = TemperatureUnit.CELSIUS.displayName): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"


fun formatMamMinTemp(
    maxTemp: Double,
    minTemp: Double,
    unit: String = TemperatureUnit.CELSIUS.displayName
): String {
    return "H: ${ formatTemperatureValue(maxTemp,unit)}  L: ${ formatTemperatureValue(minTemp,unit)}"
}


private fun getUnitSymbols(unit: String) = when (unit) {
    TemperatureUnit.CELSIUS.displayName -> TemperatureUnit.CELSIUS.symbol
    TemperatureUnit.FAHRENHEIT.displayName -> TemperatureUnit.FAHRENHEIT.symbol
    else -> "N/A"
}

fun formatWindValue(speed: Double): String =
    "${speed.roundToInt()} ${Constants.windSpeedUnit}"

fun formatHumidityValue(humidity: Int): String =
    "$humidity ${Constants.percentSymbol}"

fun formatPressureValue(pressure: Int): String =
    "$pressure ${Constants.pressureSymbol}"
fun resolveException(e: Exception): String {
    val message = "Something went wrong.Please try again later."
    when (e) {
        is NoNetworkException ->{
            return "Please check your internet and try again later"
        }
        is SocketTimeoutException -> {
            return e.message ?: "An error occurred while trying to connect to the service. Please try again later"
        }
        is ConnectException ->{
            return "Please check your internet and try again later"
        }
        is SocketException -> {
            return "Socket error occurred.Please check your internet and try again later."
        }
        is ConnectionShutdownException -> {
            return "Connection shutdown. Please check your internet and try again later"
        }
        is UnknownHostException, is SSLHandshakeException -> {
            return "Unable to connect to server.Please try again later."
        }
    }
    if (e is HttpException) {
        val errorResponseJson = e.response()?.errorBody()?.string()
        if (!errorResponseJson.isNullOrEmpty()) {
            return try {
                val errorJson = JSONObject(errorResponseJson)
                val nestedError = errorJson.optJSONObject("error")
                nestedError?.optString("message", message) ?: message
            } catch (ex: Exception) {
                message
            }
        }
    }

    return message
}


