package com.example.weathercity.data.remote

import com.example.weathercity.data.local.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String = "91c0bb41bae6488a820125743250407", // key
        @Query("q") city: String, // имя города
        @Query("days") days: Int = 6, // количество прогнозируемых дней(по умолчанию 6)
        @Query("aqi") aqi: String = "no", // данные о загрязнении воздуха
        @Query("alerts") alerts: String = "no" // предупреждения о погоде
    ): WeatherResponse
}
