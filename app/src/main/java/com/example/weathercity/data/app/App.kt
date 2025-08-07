package com.example.weathercity.data.app

import android.app.Application
import androidx.room.Room
import com.example.weathercity.data.local.WeatherDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    lateinit var database: WeatherDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }
}
