package com.example.weatherpal.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun backgroundCurrentWeather(){
    val colorTop = Color(0xFF2A9B35)   // Your first hex color
    val colorBottom = Color(0xFFEDDD53) // Your second hex color
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
fun CurrentWeather(){


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(top = 100.dp),
    ) {
//        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.15f))
        Text(
            text = "Weather Pal",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 50.sp,
                color = Color(0xFFB71C1C)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Current Weather",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = Bold,
                fontSize = 30.sp,
                color = Color(0xFF0D47A1)
            )
        )
    }
}
