package com.example.cityweather.Screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.cityweather.Screen
import com.example.cityweather.WeatherViewModel

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
