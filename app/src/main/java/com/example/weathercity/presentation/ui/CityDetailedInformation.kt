package com.example.weathercity.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathercity.presentation.viewmodel.WeatherViewModel

// Функция, создающая Card с детельной информацией о погоде в городе
@Composable
fun CityDetailedInformation(
    viewModel: WeatherViewModel,
    onBackCityCard: () -> Unit,
) {
    val weather = viewModel.currencyCity.collectAsState().value

    if (weather == null) {
        Text(
            text = "Weather data not loaded",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
        backgroundColor = Color(0xFFF9F9FF),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = {
                onBackCityCard()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go Back" // кнопка, возвращающая назад
                )
            }
            // Инфа о городе
            Text("Location", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("City: ${weather.location.name}")
            Text("Country: ${weather.location.country}")
            Text("Local time: ${weather.location.localtime}")

            // Текущая погода
            Spacer(Modifier.height(12.dp))
            Text("Current Weather", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Temperature: ${weather.current.temp_c} °C")
            Text("Feels like: ${weather.current.feelslike_c} °C")
            Text("Condition: ${weather.current.condition.text}")
            Text("Wind speed: ${weather.current.wind_mph} mph")
            Text("Humidity: ${weather.current.humidity}%")

            // Прогноз на следующие 5 дней
            Spacer(Modifier.height(12.dp))
            Text("5-Day Forecast", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            weather.forecast.forecastday.take(5).forEach { forecast ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text("Date: ${forecast.date}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Text("Max temp: ${forecast.day.maxtemp_c} °C")
                    Text("Min temp: ${forecast.day.mintemp_c} °C")
                    Text("Condition: ${forecast.day.condition.text}")
                    Text("Max wind: ${forecast.day.feelslike_c} kph")
                }
            }
        }
    }
}

