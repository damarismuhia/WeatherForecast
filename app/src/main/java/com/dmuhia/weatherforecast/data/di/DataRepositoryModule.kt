package com.dmuhia.weatherforecast.data.di

import android.net.ConnectivityManager
import com.dmuhia.weatherforecast.data.local.LocalWeatherDataSource
import com.dmuhia.weatherforecast.data.remote.RemoteWeatherDataSource
import com.dmuhia.weatherforecast.data.repo.DefaultWeatherRepositoryImpl
import com.dmuhia.weatherforecast.domain.DefaultWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(weatherRepositoryImpl: DefaultWeatherRepositoryImpl): DefaultWeatherRepository
}



