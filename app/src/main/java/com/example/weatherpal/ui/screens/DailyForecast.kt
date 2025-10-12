package com.example.weatherpal.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import com.example.weatherpal.TemperatureUnit
import com.example.weatherpal.models.Weather
import com.example.weatherpal.models.Forecast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyForecastScreen(weather: Weather?, unit: TemperatureUnit){
    Box(modifier = Modifier.fillMaxSize()) {
        Background()
        Title()
        ForecastTable(forecastList = weather?.forecast, unit = unit)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastTable(forecastList: List<Forecast>?, unit: TemperatureUnit) {
    if (forecastList != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
        ) {
            Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.3f))

            Text(
                text = "Daily Forecast",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = Bold,
                    fontSize = 30.sp,
                    color = Color(0xFF0D47A1)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(items = forecastList) { forecastItem ->
                    // Convert Celsius to Fahrenheit
                    val high = if (unit == TemperatureUnit.CELSIUS) forecastItem.highTemp else toFahrenheit(forecastItem.highTemp)
                    val low = if (unit == TemperatureUnit.CELSIUS) forecastItem.lowTemp else toFahrenheit(forecastItem.lowTemp)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    ) {
                        //Date
                        Text(
                            text = forecastItem.date.format(DateTimeFormatter.ofPattern("E dd")),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Image
                        Image(
                            painter = painterResource(id = forecastItem.weatherIcon),
                            contentDescription = forecastItem.condition,
                            modifier = Modifier.size(48.dp).padding(bottom = 12.dp)
                        )
                        //Temps high then low
                        Text(
                            text = "$high°/$low°",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Condition
                        Text(
                            text = forecastItem.condition,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Precipitation type
                        Text(
                            text = forecastItem.precipitationType,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Precipitation amount and probability
                        Text(
                            text = "${forecastItem.precipitationAmount}mm (${forecastItem.precipitationProbability}%)",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Wind speed
                        Text(
                            text = "${forecastItem.windSpeed} km/h",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Wind direction
                        Text(
                            text = forecastItem.windDirection,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        //Humidity
                        Text(
                            text = "${forecastItem.humidity}% humidity",
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }
    }
}


