package com.example.weathercity.data.local.model

import com.example.weathercity.data.remote.model.Current
import com.example.weathercity.data.remote.model.Forecast
import com.example.weathercity.data.remote.model.Location

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)