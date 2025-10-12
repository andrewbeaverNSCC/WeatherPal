package com.example.weatherpal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherpal.ui.screens.CurrentWeatherScreen
import com.example.weatherpal.ui.screens.DailyForecastScreen
import com.example.weatherpal.ui.theme.WeatherPalTheme
import androidx.activity.viewModels
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherpal.ui.screens.CurrentWeather

// icons from https://www.flaticon.com/search?author_id=1264
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherPalTheme {
                DisplayUI(mainViewModel)
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayUI(mainViewModel: MainViewModel){

    //collectAsState makes UI automatically update when weatherState changes
    val weatherState by mainViewModel.weather.collectAsState()
    val unitState by mainViewModel.unit.collectAsState()

    val location = "Halifax, NS"

    val navController = rememberNavController()

    // Store the selectedIndex in a state flow variable
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        //Display top bar
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF3E4E32),
                    titleContentColor = Color(0xFFFAFAFA)
                ),
                title = {
                    Text(location)
                },
                actions = {
                    TextButton(onClick = { mainViewModel.toggleUnit() }){
                        Text(
                            text = if (unitState == TemperatureUnit.CELSIUS) "°C" else "°F",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFFFAFAFA)

                        )
                    }
                }


            )
        },
        //Display bottom bar
        bottomBar = {
            // Display a navigation bar
            NavigationBar (
                containerColor = Color(0xFF2F402C)
            )
            {
                // Current Weather screen
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_current_weather),
                            contentDescription = "Current Weather"
                        )
                    },
                    label = {
                        Text("Current Weather")
                    },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        navController.navigate(route = "Current Weather")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.LightGray,
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color.White,
                        selectedIconColor = Color.White,
                        indicatorColor = Color(0xFF3E4E32)
                    )
                )

                // Daily Forecast screen
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_action_daily_forecast),
                            contentDescription = "Daily Forecast"
                        )
                    },
                    label = {
                        Text("Daily Forecast")
                    },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate(route = "Daily Forecast")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.LightGray,
                        unselectedIconColor = Color.LightGray,
                        selectedTextColor = Color.White,
                        selectedIconColor = Color.White,
                        indicatorColor = Color(0xFF3E4E32)
                    )
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "Current Weather",
            modifier = Modifier.padding(innerPadding)
        )
        {
            // Render Current Weather screen
            composable(route = "Current Weather") {
                CurrentWeatherScreen(weather = weatherState, unit = unitState)
            }

            // Render Daily Forecast screen
            composable(route = "Daily Forecast") {
                DailyForecastScreen(weather = weatherState, unit = unitState)
            }
        }

    }
}
