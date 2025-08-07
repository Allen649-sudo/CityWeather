package com.example.weathercity.domain.usecase

import com.example.weathercity.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String) = repository.getWeather(city)
}
