package com.dmuhia.weatherforecast.utils

enum class TemperatureUnit(val symbol: String, val displayName: String) {
    CELSIUS("°C", "Celsius"),
    FAHRENHEIT("°F", "Fahrenheit");

    override fun toString(): String {
        return "$displayName ($symbol)"
    }
}
