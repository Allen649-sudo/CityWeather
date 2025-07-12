package com.example.cityweather.dataClasses


data class Current(
    val temp_c: Double,
    val condition: Condition,
    val wind_mph: Double,
    val feelslike_c: Double,
    val humidity: Int
)