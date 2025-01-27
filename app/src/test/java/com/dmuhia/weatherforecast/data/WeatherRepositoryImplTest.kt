package com.dmuhia.weatherforecast.data

import com.dmuhia.weatherforecast.data.local.room.WeatherDao
import com.dmuhia.weatherforecast.data.remote.WeatherApiService
import com.dmuhia.weatherforecast.utils.ApiResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class WeatherRepositoryImplTest {
    private lateinit var weatherApiService: WeatherApiService
    private lateinit var weatherDao: WeatherDao
//    private lateinit var weatherRepository: WeatherRepositoryImpl
//
//    @Before
//    fun setup() {
//        weatherApiService = mockk()
//        weatherDao = mockk()
//        weatherRepository = WeatherRepositoryImpl(weatherApiService, weatherDao)
//    }
//    @Test
//    fun `test getCurrentWeatherDetails returns success`() = runTest {
//
//        coEvery { weatherApiService.getCurrentWeather(any(), any()) } returns weatherResponse
//        val result = weatherRepository.getCurrentWeatherDetails()
//        assertTrue("Expected ApiResponse.Success, but got ${result::class.simpleName}",
//            result is ApiResponse.Success,
//        )
//        val data = (result as ApiResponse.Success).data
//        assertTrue(data?.cityName == "Nairobi")
//
//       coVerify { weatherApiService.getCurrentWeather(any(), any()) }
//    }
//    @Test
//    fun `test getCurrentWeatherDetails returns error`(): Unit = runTest {
//        // Given
//        val exception = Exception("Network error")
//        coEvery { weatherApiService.getCurrentWeather(any(), any()) } throws exception
//
//        // When
//        val result = weatherRepository.getCurrentWeatherDetails()
//
//        //Then
//        assertTrue("Expected ApiResponse.Error",
//            result is ApiResponse.Error,
//        )
//
//    }
//
//    // getWeatherForDate
//    @Test
//    fun `test getWeatherForDate returns data from local database`(): Unit = runTest {
//        coEvery { weatherDao.getWeatherForDate("2024-12-04") } returns weatherWithHourlyWeather
//
//        val result = weatherRepository.getWeatherForDate("2024-12-04")
//
//        assertTrue(result is ApiResponse.Success)
//        val data = (result as ApiResponse.Success).data
//        if (data != null) {
//            assertTrue(data.isFromLocal)
//        }
//        coVerify { weatherDao.getWeatherForDate("2024-12-04") }
//    }
//
//    @Test
//    fun `test getWeatherForDate returns data from remote API and caches it`() = runTest {
//        coEvery { weatherDao.getWeatherForDate(any()) } returns null
//        coEvery { weatherApiService.getWeatherForDate(any(), any(), any()) } returns forecastResponse
//        coEvery { weatherDao.insertWeather(any()) } just Runs
//        coEvery { weatherDao.insertHourlyWeather(any()) } just Runs
//
//        val result = weatherRepository.getWeatherForDate("2024-12-04")
//
//
//        assertTrue("Expected ApiResponse.Success but got ${result::class.simpleName}", result is ApiResponse.Success)
//        val data = (result as ApiResponse.Success).data
//        if (data != null) {
//            assertFalse("Expected data to not be from local cache", data.isFromLocal)
//        }
//
//        coVerify { weatherApiService.getWeatherForDate(any(), any(), any()) }
//        coVerify { weatherDao.insertWeather(any()) }
//        coVerify { weatherDao.insertHourlyWeather(any()) }
//    }


}