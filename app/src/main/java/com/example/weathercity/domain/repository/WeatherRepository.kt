package com.example.weathercity.domain.repository

import com.example.weathercity.data.local.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(city: String): WeatherResponse
}
