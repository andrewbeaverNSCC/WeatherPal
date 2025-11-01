package com.example.weatherpal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherpal.models.Condition
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.weatherpal.models.Weather
import com.example.weatherpal.models.Current
import com.example.weatherpal.models.Day
import com.example.weatherpal.models.Forecast
import com.example.weatherpal.models.ForecastDay
import com.example.weatherpal.services.WeatherService
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ln

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

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Define retrofit instance with baseUrl = https://api.weatherapi.com

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)


    fun fetchWeather(location: String){
        viewModelScope.launch {

            try {
                val weatherResponse = weatherService.getWeather(
                    apiKey = "4b3435be3ba74f1f977174737251510",
                    query = location,
                    days = 5,
                    aqi = "no",
                    alerts = "yes"
                )
                _weather.value = weatherResponse
            } catch (e: Exception) {
                Log.e("MainViewModel", "Failed to fetch weather", e)

            }
        }
    }


}