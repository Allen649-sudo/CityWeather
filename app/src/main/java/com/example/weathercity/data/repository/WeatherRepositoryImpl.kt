package com.example.weathercity.data.repository

import com.example.weathercity.domain.repository.WeatherRepository
import com.example.weathercity.data.local.model.WeatherResponse
import com.example.weathercity.data.remote.WeatherApi
import com.example.weathercity.data.local.WeatherDao
import com.example.weathercity.data.local.WeatherEntity
import com.google.gson.Gson
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeather(city: String): WeatherResponse {
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