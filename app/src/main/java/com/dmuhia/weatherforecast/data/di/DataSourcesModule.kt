package com.dmuhia.weatherforecast.data.di

import com.dmuhia.weatherforecast.data.local.LocalWeatherDataSource
import com.dmuhia.weatherforecast.data.local.LocalWeatherDataSourceImpl
import com.dmuhia.weatherforecast.data.remote.RemoteWeatherDataSource
import com.dmuhia.weatherforecast.data.remote.RemoteWeatherDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourcesModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalWeatherDataSourceImpl): LocalWeatherDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteWeatherDataSourceImpl): RemoteWeatherDataSource
}
