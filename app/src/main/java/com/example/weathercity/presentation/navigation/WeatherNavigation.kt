package com.example.weathercity.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.weathercity.presentation.ui.CityDetailedInformation
import com.example.weathercity.presentation.ui.DefaultCity
import com.example.weathercity.presentation.ui.PopularCitiesCardList
import com.example.weathercity.presentation.ui.SearchCity
import androidx.compose.animation.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weathercity.presentation.viewmodel.WeatherViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object SearchCity : Screen("searchCity", "Search", Icons.Default.Search)
    object PopularCities : Screen("popularCities", "Popular", Icons.Default.Star)
    object DefaultCity : Screen("defaultCity", "Default", Icons.Default.Favorite)
    object CityDetailedInformation : Screen("cityDetailedInformation", "", Icons.Default.Info) // не отображается в BottomNav
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CurrencyNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: WeatherViewModel = hiltViewModel()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.SearchCity.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
        ) {
            composable(Screen.SearchCity.route) {
                SearchCity( // экран с поиско городов
                    viewModel,
                    onNavigateCityDetail = {
                        navController.navigate(Screen.CityDetailedInformation.route)
                    }
                )
            }

            composable(Screen.PopularCities.route) {
                PopularCitiesCardList( // экран с популярными городами
                    viewModel,
                    showDetailInfo = {
                        viewModel.addCurrencyCity(it)
                        navController.navigate(Screen.CityDetailedInformation.route)
                    },
                    onAddDefaultCity = { viewModel.addDefaultCity(it) },
                    onDeleteInPopular = { viewModel.deletePopularCity(it) }
                )
            }

            composable(Screen.DefaultCity.route) {
                DefaultCity( // экран с дефолтным городом
                    viewModel,
                    onNavigateCityDetail = {
                        navController.navigate(Screen.CityDetailedInformation.route)
                    }
                )
            }

            composable(Screen.CityDetailedInformation.route) {
                CityDetailedInformation( // детальная информация о городе
                    viewModel,
                    onBackCityCard = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}