package com.example.weatherpal.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

// http://api.weatherapi.com/v1/forecast.json?key=4b3435be3ba74f1f977174737251510

data class Weather(
    val current: Current,
    val forecast: Forecast
)

data class Current(
    @SerializedName("temp_c") val temperature: Double,
    @SerializedName("feelslike_c") val feelsLike: Double,
    @SerializedName("wind_kph") val windSpeed: Double,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("precip_mm") val precipitation: String,

    val condition: Condition,
)

data class Condition(
    val text: String,
    val icon: String,

)

data class Forecast(
    @SerializedName("forecastday") val forecastDay: List<ForecastDay>
)




data class Day(
    @SerializedName("maxtemp_c") val highTemp: Double,
    @SerializedName("mintemp_c") val lowTemp: Double,
    @SerializedName("maxwind_kph") val windSpeed: Double,
    @SerializedName("totalprecip_mm") val precipitationAmount: Double,
    @SerializedName("avghumidity") val humidity: Int,
    @SerializedName("daily_chance_of_rain") val precipitationProbability: Int,

    val condition: Condition
)

data class ForecastDay(
    val date: String,
    val day: Day
)
