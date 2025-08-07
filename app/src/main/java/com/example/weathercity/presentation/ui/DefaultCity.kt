package com.example.weathercity.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercity.presentation.viewmodel.WeatherViewModel

// Функция, которая отвечает за отображение города по умолчанию
//Можно сделать любой город по умолчанию
@Composable
fun DefaultCity(
    viewModel: WeatherViewModel,
    onNavigateCityDetail: () -> Unit,
) {
    val defaultCity by viewModel.defaultCity.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "DEFAULT CITIES",
            fontSize = 28.sp,
            color = Color(0xFF5D00FF),
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.CenterHorizontally)
        )
        CityCardList(defaultCity, showDetailInfo = {
            viewModel.addCurrencyCity(it)
            onNavigateCityDetail()
        },
            onAddDefaultCity = {
                viewModel.addDefaultCity(it)
            }, // сделать данный город дефолтным
            onAddPopularCityList = {
                viewModel.addPopularCity(it)
            }, // добавить данный город к списку популярных городов
            onAddDefaultCityBool = false
        )
    }
}
