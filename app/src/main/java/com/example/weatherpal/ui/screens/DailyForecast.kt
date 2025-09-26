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
import com.example.weatherpal.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items

data class DailyForecast(
    //Date
    var date: LocalDate,
    //Icon
    var weatherIcon: Int,
    //Temp high
    var highTemp: Double,
    //Temp low
    var lowTemp: Double,
    //Condition
    var condition: String,
    //Precipitation type
    var precipitationType: String,
    //Precipitation amount
    var precipitationAmount: Double,
    //Precipitation %
    var precipitationProbability: Int,
    //Wind direction
    var windDirection: String,
    //Wind speed
    var windSpeed: Double,
    //humidity
    var humidity: Int
)


@RequiresApi(Build.VERSION_CODES.O)
val sampleForecast = listOf(
    DailyForecast(LocalDate.now().plusDays(1), R.drawable.sun, 20.0, 12.0, "Sunny", "None", 0.0, 0, "NE", 10.5, 45),
    DailyForecast(LocalDate.now().plusDays(2), R.drawable.raining, 14.0, 8.0, "Rain", "Rain", 5.2, 80, "W", 12.3, 60),
    DailyForecast(LocalDate.now().plusDays(3), R.drawable.partly_cloudy, 10.0, 8.0, "Cloudy", "None", 0.0, 10, "E", 8.0, 50),
    DailyForecast(LocalDate.now().plusDays(4), R.drawable.lightning, 6.0, 2.0, "Thunderstorms", "Rain", 7.1, 90, "SW", 15.0, 70),
    DailyForecast(LocalDate.now().plusDays(5), R.drawable.snowfall, 4.0, -2.0, "Snow", "Snow", 12.0, 75, "N", 20.0, 80)
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyForecastScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize()) {
        Background()
        Title()
        ForecastTable(sampleForecast)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastTable(forecastList: List<DailyForecast>){
    Column (
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                ){
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
                    text = "${forecastItem.highTemp.toInt()}°/${forecastItem.lowTemp.toInt()}°",
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
                Text (
                    text = forecastItem.windDirection,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                    //Humidity
                Text(
                    text = "${forecastItem.humidity}% humidity",
                    modifier = Modifier.padding(bottom = 12.dp)
                )}
            }
        }
}
}


