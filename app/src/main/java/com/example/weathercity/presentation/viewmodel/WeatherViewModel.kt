package com.example.weathercity.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercity.data.repository.PopularCities
import com.example.weathercity.data.local.model.WeatherResponse
import com.example.weathercity.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
    // город, который ищем
    private val _weatherResponse = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponse: StateFlow<WeatherResponse?> get() = _weatherResponse

    // текст с ошибкой
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    // список популярных городов
    private val _weatherList = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val  weatherList: StateFlow<List<WeatherResponse>> get() = _weatherList

    // текущий выбранный город
    private val _currencyCity = MutableStateFlow<WeatherResponse?>(null)
    val currencyCity: StateFlow<WeatherResponse?> get() = _currencyCity

    // город по умолчанию
    private val _defaultCity = MutableStateFlow<WeatherResponse?>(null)
    val defaultCity: StateFlow<WeatherResponse?> get() = _defaultCity

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    val cities = PopularCities.cities

    init {
        loadPopularCities() // Загружаем при старте приложения список популярных городов
    }

    fun searchCity(city: String) { // функция отвечает за поиск города
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getWeatherUseCase(city)
                _weatherResponse.value = result
                _errorMessage.value = null // сбрасываем ошибку
            } catch (e: Exception) {
                _weatherResponse.value = null
                _errorMessage.value = "Данного города не существует"
            }
        }
    }

    fun loadPopularCities() { // загружаем список популярных городов(PopularCities)
        viewModelScope.launch(Dispatchers.IO) {
            val resultList = mutableListOf<WeatherResponse>()
            cities.forEach { city ->
                try {
                    val result = getWeatherUseCase(city)
                    resultList.add(result)
                } catch (e: Exception) {

                }
            }
            _weatherList.value = resultList
        }
    }

    fun deletePopularCity(city: WeatherResponse) { // удаляем переданный город из списка популярных городов
        viewModelScope.launch(Dispatchers.IO) {
            _weatherList.update { list ->
                list.filterNot { it == city }
            }
        }
    }

    fun addPopularCity(city: WeatherResponse) { // добавляем переданный город в список популярных городов
        viewModelScope.launch(Dispatchers.IO) {
            _weatherList.update { list ->
                if (list.any { it.location.name.equals(city.location.name, ignoreCase = true) }) {
                    list // уже есть — не добавляем
                } else {
                    list + city // нет такого — добавляем
                }
            }
        }
    }

    fun refreshPopularCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.value = true
            loadPopularCities()
            _isRefreshing.value = false
        }
    }

    fun addCurrencyCity(currencyCity: WeatherResponse) // добавить текущий город
    {
        _currencyCity.value = currencyCity
    }

    fun addDefaultCity(defaultCity: WeatherResponse) // добавить город по умолчанию
    {
        _defaultCity.value = defaultCity
    }
}