package com.example.weatherpal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherpal.models.Condition
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.weatherpal.models.Weather
import com.example.weatherpal.models.Current
import com.example.weatherpal.models.Day
import com.example.weatherpal.models.Forecast
import com.example.weatherpal.models.ForecastDay
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                temperature = 25.0,
                feelsLike = 28.0,
                windSpeed = 10.0,
                windDirection = "North",
                precipitation = "Clear",
                condition = Condition(
                    text = "Sunny",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                )
            ),
            forecast = Forecast(
                forecastDay = listOf(
                    ForecastDay(
                        date = LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE),
                        day = Day(
                        highTemp = 20.0,
                        lowTemp = 12.0,
                        precipitationAmount = 0.0,
                        precipitationProbability = 0,
                        windSpeed = 10.5,
                        humidity = 45,
                        condition = Condition(
                            text = "Sunny",
                            icon = "//icon-url.com/sunny.png"
                        )
                        )
                ),
                ForecastDay(
                    date = LocalDate.now().plusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    day = Day(
                    highTemp = 14.0,
                    lowTemp = 8.0,
                    precipitationAmount = 5.2,
                    precipitationProbability = 80,
                    windSpeed = 12.3,
                    humidity = 60,
                    condition = Condition(
                        text = "Partly cloudy",
                        icon = "//icon-url.com/partly_cloudy.png"
                    )
                    )
                ),
                ForecastDay(
                    date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    day = Day(
                    highTemp = 10.0,
                    lowTemp = 8.0,
                    precipitationAmount = 0.0,
                    precipitationProbability = 10,
                    windSpeed = 8.0,
                    humidity = 50,
                    condition = Condition(
                        text = "Sunny",
                        icon = "//icon-url.com/sunny.png"
                    )
                    )
                ),
                ForecastDay(
                    date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    day = Day(
                    highTemp = 6.0,
                    lowTemp = 2.0,
                    precipitationAmount = 7.1,
                    precipitationProbability = 90,
                    windSpeed = 15.0,
                    humidity = 70,
                    condition = Condition(
                        text = "Cloudy",
                        icon = "//icon-url.com/cloudy.png"
                    )
                    )
                ),
                ForecastDay(
                    date = LocalDate.now().plusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    day = Day(
                        highTemp = 4.0,
                        lowTemp = -2.0,
                        precipitationAmount = 12.0,
                        precipitationProbability = 75,
                        windSpeed = 20.0,
                        humidity = 80,
                        condition = Condition(
                            text = "Rainy",
                            icon = "//icon-url.com/rainy.png"
                    )
                )
            )
                )
            )
        )
    }
}