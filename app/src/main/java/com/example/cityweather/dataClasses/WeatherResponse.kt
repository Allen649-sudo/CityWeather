package com.example.cityweather.dataClasses

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)