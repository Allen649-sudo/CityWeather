package com.example.weathercity.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val json: String // сериализованный WeatherResponse
)