package com.example.weatherpal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.weatherpal.models.Weather
import com.example.weatherpal.models.Current
import com.example.weatherpal.models.Forecast
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

// Enum class for temperature units
enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT
}

class MainViewModel : ViewModel(){

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather = _weather.asStateFlow()

    // Toggle between Celsius and Fahrenheit
    private val _unit = MutableStateFlow(TemperatureUnit.CELSIUS)
    val unit = _unit.asStateFlow()
    fun toggleUnit() {
        if (_unit.value == TemperatureUnit.CELSIUS) {
            _unit.value = TemperatureUnit.FAHRENHEIT
        } else {
            _unit.value = TemperatureUnit.CELSIUS
        }
    }

    init {
        _weather.value = Weather(
            current = Current(
                temperature = 25,
                feelsLike = 28,
                condition = "Sunny",
                windSpeed = 10,
                windDirection = "North",
                precipitation = "Clear",
                weatherIcon = R.drawable.sun
            ),
            forecast = listOf(
                Forecast(
                    date = LocalDate.now().plusDays(1),
                    highTemp = 20.0,
                    lowTemp = 12.0,
                    condition = "Sunny",
                    precipitationType = "None",
                    precipitationAmount = 0.0,
                    precipitationProbability = 0,
                    windDirection = "NE",
                    windSpeed = 10.5,
                    humidity = 45,
                    weatherIcon = R.drawable.sun
                ),
                Forecast(
                    date = LocalDate.now().plusDays(2),
                    highTemp = 14.0,
                    lowTemp = 8.0,
                    condition = "Rain",
                    precipitationType = "Rain",
                    precipitationAmount = 5.2,
                    precipitationProbability = 80,
                    windDirection = "W",
                    windSpeed = 12.3,
                    humidity = 60,
                    weatherIcon = R.drawable.raining
                ),
                Forecast(
                    date = LocalDate.now().plusDays(3),
                    highTemp = 10.0,
                    lowTemp = 8.0,
                    condition = "Cloudy",
                    precipitationType = "None",
                    precipitationAmount = 0.0,
                    precipitationProbability = 10,
                    windDirection = "E",
                    windSpeed = 8.0,
                    humidity = 50,
                    weatherIcon = R.drawable.partly_cloudy
                ),
                Forecast(
                    date = LocalDate.now().plusDays(4),
                    highTemp = 6.0,
                    lowTemp = 2.0,
                    condition = "Thunderstorms",
                    precipitationType = "Rain",
                    precipitationAmount = 7.1,
                    precipitationProbability = 90,
                    windDirection = "SW",
                    windSpeed = 15.0,
                    humidity = 70,
                    weatherIcon = R.drawable.lightning
                ),
                Forecast(
                    date = LocalDate.now().plusDays(5),
                    highTemp = 4.0,
                    lowTemp = -2.0,
                    condition = "Snow",
                    precipitationType = "Snow",
                    precipitationAmount = 12.0,
                    precipitationProbability = 75,
                    windDirection = "N",
                    windSpeed = 20.0,
                    humidity = 80,
                    weatherIcon = R.drawable.snowfall
                )
            )
        )
    }
}