package com.dmuhia.weatherforecast.utils

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import com.dmuhia.weatherforecast.WeatherApplication
import com.dmuhia.weatherforecast.utils.Constants.getRootFunction

fun checkRooted(): Boolean {
    val checker: Int = getRootFunction
    return checker == 1
}

fun isDeviceSecured(): Boolean {
    val keyguardManager = WeatherApplication.applicationContext()?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager //api 16+
    return keyguardManager.isDeviceSecure   //Return whether the keyguard is secured by a PIN, pattern or password or a SIM card is currently locked.
}
fun isVM(): Boolean {
    val radioVersion = Build.getRadioVersion()
    return radioVersion == null || radioVersion.isEmpty() || radioVersion == "1.0.0.0"
}


