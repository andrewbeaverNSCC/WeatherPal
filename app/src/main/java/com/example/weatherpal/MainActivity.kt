package com.example.weatherpal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weatherpal.ui.screens.CurrentWeatherScreen
import com.example.weatherpal.ui.screens.DailyForecastScreen
import com.example.weatherpal.ui.theme.WeatherPalTheme

// icons from https://www.flaticon.com/search?author_id=1264
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherPalTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //CurrentWeatherScreen()
                    DailyForecastScreen()
                }
            }
        }
    }
}
