package com.example.cityweather.dataClasses

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val feelslike_c: Double,
    val condition: Condition
)