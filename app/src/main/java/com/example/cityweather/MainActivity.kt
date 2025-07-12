package com.example.cityweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.cityweather.Screens.SearchCity
import com.example.cityweather.ui.theme.CityWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CityWeatherTheme {
                val navController = rememberNavController()
                CurrencyNavigation(navController)
            }
        }
    }
}

