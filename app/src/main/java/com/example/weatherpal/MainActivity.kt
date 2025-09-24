package com.example.weatherpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherpal.ui.screens.CurrentWeather
import com.example.weatherpal.ui.screens.backgroundCurrentWeather
import com.example.weatherpal.ui.theme.WeatherPalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Keep if you use it
        setContent {
            WeatherPalTheme {
                // Option 1: Simplest display in a Surface
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    backgroundCurrentWeather()
                    CurrentWeather()

                }

                // Option 2: If you had the Scaffold structure from the default template
                // Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                //    CurrentWeather(
                //        modifier = Modifier.padding(innerPadding) // Pass padding if CurrentWeather accepts it
                //    )
                // }
            }
        }
    }
}

//class MainActivity : ComponentActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContent {
////            WeatherPalTheme {
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
////                }
////            }
////        }
////    }
////}
////
////@Composable
////fun Greeting(name: String, modifier: Modifier = Modifier) {
////    Text(
////        text = "Hello $name!",
////        modifier = modifier
////    )
////}
////
////@Preview(showBackground = true)
////@Composable
////fun GreetingPreview() {
////    WeatherPalTheme {
////        Greeting("Android")
////    }
////}
