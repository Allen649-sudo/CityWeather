package com.example.weathercity.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weathercity.R
import com.example.weathercity.presentation.viewmodel.WeatherViewModel

// В этой функции мы можем найти любой город/
// Мы пишем желаемый город, и отправляем запрос на сервер, где нам приходит ответ, который мы парсим и выводим на экран
//
@Composable
fun SearchCity(
    viewModel: WeatherViewModel,
    onNavigateCityDetail: () -> Unit,
) {
    val weatherResponse by viewModel.weatherResponse.collectAsState()

    val errorMessage by viewModel.errorMessage.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchCity(searchQuery)
            },
            label = { Text(
                text = stringResource(id = R.string.INPUT_CITY)
            ) }
        )

            CityCardList(weatherResponse, showDetailInfo = {
                viewModel.addCurrencyCity(it)
                onNavigateCityDetail()
            }, // если данный город существует, вызываем функцию CityCardList, которая отображает карточку о городе
            onAddDefaultCity = {
                viewModel.addDefaultCity(it)
            },
            onAddPopularCityList = {
                viewModel.addPopularCity(it)
            })

        if (errorMessage != null && searchQuery.isNotBlank()) {
            Text( // если данного города нет, то выводится errorMessage(он в ViewModel)
                text = errorMessage ?: "",
                modifier = Modifier.padding(top = 8.dp)
            )
        }

    }
}
