package com.example.weathercity.presentation.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercity.R
import com.example.weathercity.presentation.viewmodel.WeatherViewModel
import com.example.weathercity.data.local.model.WeatherResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


// Функция, которая выводит на экран список популярных городов
// В данном списке можно добавлять города и удалять города
@Composable
fun PopularCitiesCardList(
    viewModel: WeatherViewModel,
    showDetailInfo: (WeatherResponse) -> Unit,
    onAddDefaultCity: (WeatherResponse) -> Unit,
    onDeleteInPopular: (WeatherResponse) -> Unit,
) {
    val weatherList by viewModel.weatherList.collectAsState() // список популярных городов
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Spacer(modifier = Modifier.height(70.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.POPULAR_CITIES),
            fontSize = 28.sp,
            color = Color(0xFF5D00FF),
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.CenterHorizontally)
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refreshPopularCities() }
        )
        {
            LazyColumn {
                items(weatherList) { weather ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(5.dp, RoundedCornerShape(24.dp))
                            .border(1.dp, Color(0xFF5D00FF), RoundedCornerShape(24.dp))
                            .clickable { showDetailInfo(weather) },
                        backgroundColor = Color(0xFFF6F4FC),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val textStyle = TextStyle(fontSize = 20.sp)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column {
                                    AsyncImage(
                                        model = "https:${weather.current.condition.icon}",
                                        contentDescription = "Weather Icon",
                                        modifier = Modifier.height(64.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    IconButton(onClick = {
                                        onAddDefaultCity(weather)
                                    }) { // сделать данный город дефолтным
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Add to default"
                                        )
                                    }
                                    IconButton(onClick = {
                                        onDeleteInPopular(weather)
                                    }) { // удалить данный город из списка популярных городов
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                    }
                                }

                                Column {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "City: ${weather.location.name}", fontSize = 23.sp)
                                    Text(text = "Country: ${weather.location.country}", style = textStyle)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(text = "Temperature: ${weather.current.temp_c} °C", style = textStyle)
                                    Text(text = "Feels like: ${weather.current.feelslike_c} °C", style = textStyle)
                                    Text(text = "Condition: ${weather.current.condition.text}", style = textStyle)
                                    Text(text = "Wind speed: ${weather.current.wind_mph} km/h", style = textStyle)
                                    Text(text = "Humidity: ${weather.current.humidity}%", style = textStyle)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

    }
}
