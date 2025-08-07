package com.example.weathercity.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercity.data.local.model.WeatherResponse

// Данная функция отображает одну карту города. Например, карту города по умолчанию или когда ищем город
@Composable
fun CityCardList(
    weatherResponse: WeatherResponse? = null,
    showDetailInfo: (WeatherResponse) -> Unit,
    onAddDefaultCity: (WeatherResponse) -> Unit,
    onAddDefaultCityBool: Boolean = true,
    onAddPopularCityList: (WeatherResponse) -> Unit,
) {
    if (weatherResponse == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Weather not loaded",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color(0xFF5D00FF) // фиолетовый стиль как в заголовке
                ),
                textAlign = TextAlign.Center
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(5.dp, RoundedCornerShape(24.dp))
                    .border(1.dp, Color(0xFF5D00FF), RoundedCornerShape(24.dp))
                    .clickable { showDetailInfo(weatherResponse) },
                backgroundColor = Color(0xFFF6F4FC),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        AsyncImage(
                            model = "https:${weatherResponse.current.condition.icon}", // добавляем "https:" перед URL
                            contentDescription = "Weather Icon",
                            modifier = Modifier.height(64.dp),
                            contentScale = ContentScale.Fit
                        )
                        if(onAddDefaultCityBool)
                        {
                            IconButton(onClick = {
                                onAddDefaultCity(weatherResponse)
                            }) { // Делаем данный город городом по умолчанию
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Сделать городом по умолчанию"
                                )
                            }
                            IconButton(onClick = {
                                onAddPopularCityList(weatherResponse)
                            }) { // добавляем данный город к списку популярных городов
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Добавить в список популярных городов"
                                )
                            }

                        }

                    }
                    val textStyle = TextStyle(fontSize = 20.sp)

                    // Инфа о городе
                    Text(text = "City: ${weatherResponse.location.name}", fontSize = 23.sp)
                    Text(text = "Country: ${weatherResponse.location.country}", style = textStyle)
                    Text(text = "Temperature: ${weatherResponse.current.temp_c} °C", style = textStyle)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Текущая погода
                    Text(text = "Feels like: ${weatherResponse.current.feelslike_c} °C", style = textStyle)
                    Text(text = "Condition: ${weatherResponse.current.condition.text}", style = textStyle)
                    Text(text = "Wind speed: ${weatherResponse.current.wind_mph} km/h", style = textStyle)
                    Text(text = "Humidity: ${weatherResponse.current.humidity}%", style = textStyle)

                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}