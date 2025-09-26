package com.example.weatherpal.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import com.example.weatherpal.R


@Composable
fun CurrentWeatherScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Background()
        Title()
        CurrentWeather()
    }
}
@Composable
fun Background(){
    //Design the background of screen
    val colorTop = Color(0xFFEDDD53)
    val colorBottom = Color(0xFF2A9B35)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(colorTop, colorBottom)
                )
            ),
    )
}

@Composable
fun Title(){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(top = 100.dp),
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.06f))
        Text(
            text = "Weather Pal",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 50.sp,
                color = Color(0xFFB71C1C)
            )
        )
    }
}

@Composable
fun CurrentWeather(){

    val location = "Halifax, NS"
    val temperature = "25"
    val feelsLike = "28"
    val condition = "Sunny"
    val windSpeed = "10"
    val windDirection = "North"
    val precipitation = "Clear"
    val icon = painterResource(R.drawable.sun)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.3f))

        Text(
            text = "Current Weather",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 30.sp,
                color = Color(0xFF0D47A1)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))


        //Location
        Text(
            text = location,

            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 20.sp,
                color = Color(0xFF0D47A1)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Temperature
        Text(
            text = "$temperature°C",

            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = icon,
            contentDescription = "Weather condition icon",
            modifier = Modifier.size(100.dp)
        )

        //Feels like
        Text(
            text = "Feels like $feelsLike°C",

            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(3.dp, Color.Black)
                .padding(20.dp)
        )
        {
            val weatherInfo = listOf(
                "Condition" to condition,
                "Wind Speed" to windSpeed + " km/h " + windDirection,
                "Precipitation" to precipitation
            )
            weatherInfo.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                )
                {
                    Text(
                        text = "$label:",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = value,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
