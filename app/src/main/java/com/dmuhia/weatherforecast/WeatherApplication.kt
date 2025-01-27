package com.dmuhia.weatherforecast

import android.app.Application
import android.content.Context
import com.dmuhia.weatherforecast.utils.Constants

import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class WeatherApplication: Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
        private var appInstance: WeatherApplication? = null
        fun applicationContext(): Context? {
            return appInstance?.applicationContext
        }
    }
    init {
        appInstance = this
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
        Constants.baseUrl = getBaseURL()
        Constants.cipherAlis = getCipherKey()
        Constants.apiKey = getApiKey()
    }
    private external fun getBaseURL(): String
    private external fun getApiKey(): String
    private external fun getCipherKey(): String
    private external fun rootFunction(): Int
    private fun delayedInit() {
        applicationScope.launch {
            if (BuildConfig.DEBUG)
                Timber.plant(Timber.DebugTree())
            }
        }
    }

