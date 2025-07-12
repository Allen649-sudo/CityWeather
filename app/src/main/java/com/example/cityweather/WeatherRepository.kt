package com.example.cityweather

import com.example.cityweather.dataClasses.WeatherResponse
import com.example.cityweather.retrofit.WeatherApi
import com.example.cityweather.room.WeatherDao
import com.example.cityweather.room.WeatherEntity
import com.google.gson.Gson

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val dao: WeatherDao
) {

    suspend fun getWeather(city: String): WeatherResponse {
        return try {
            val response = weatherApi.getWeather(city = city)
            // Сохраняем в БД
            val json = Gson().toJson(response)
            dao.insertWeather(WeatherEntity(city, json))
            response
        } catch (e: Exception) {
            // При ошибке (например, отсутствие интернета) — берем из БД
            val cached = dao.getWeather(city)
            if (cached != null) {
                Gson().fromJson(cached.json, WeatherResponse::class.java)
            } else {
                throw e // Если нет и в кэше — пробрасываем ошибку
            }
        }
    }
}
