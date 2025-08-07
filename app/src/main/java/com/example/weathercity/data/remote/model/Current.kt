package com.example.weathercity.data.remote.model


data class Current(
    val temp_c: Double,
    val condition: Condition,
    val wind_mph: Double,
    val feelslike_c: Double,
    val humidity: Int
)