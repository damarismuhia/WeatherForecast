package com.dmuhia.weatherforecast.data.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.dmuhia.weatherforecast.BuildConfig
import com.dmuhia.weatherforecast.data.remote.LiveNetworkMonitor
import com.dmuhia.weatherforecast.data.remote.NetworkMonitor
import com.dmuhia.weatherforecast.data.remote.NetworkMonitorInterceptor
import com.dmuhia.weatherforecast.data.remote.WeatherApiService
import com.dmuhia.weatherforecast.utils.Constants
import com.dmuhia.weatherforecast.utils.Constants.baseUrl
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideContext(application:Application):Context {
        return  application.applicationContext
    }
    @Provides
    fun provideConnectivity(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor{ message -> Timber.e(message) }.apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
    }
    @Provides
    fun provideNetworkMonitor(
        context: Context
    ): NetworkMonitor {
        return LiveNetworkMonitor(context)
    }

    @Singleton
    @Provides
    fun provideConnectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(spec: ConnectionSpec, liveNetworkMonitor: NetworkMonitor,
                            loggingInterceptor: HttpLoggingInterceptor) :OkHttpClient {
        val okHttpClientBuilder =  OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .hostnameVerifier { _, _ -> true }
            .connectionSpecs(Collections.singletonList(spec))
            .addInterceptor(NetworkMonitorInterceptor(liveNetworkMonitor))
            .addInterceptor{chain: Interceptor.Chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("appid",Constants.apiKey)
                    .build()
                val requestBuilder = originalRequest.newBuilder()
                    .url(url)
                    .header("Content-Type","application/json")
                    .addHeader("Accept", "application/json")
                val request = requestBuilder.build()
                Timber.d("Request Headers:::: ${request.headers.toMultimap()}")
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG){
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }



}