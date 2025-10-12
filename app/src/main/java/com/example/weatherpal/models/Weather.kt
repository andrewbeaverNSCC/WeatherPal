package com.example.weatherpal.models

import java.time.LocalDate


data class Weather(
    val current: Current,
    val forecast: List<Forecast>
)

data class Current(
    val temperature: Int,
    val feelsLike: Int,
    val condition: String,
    val windSpeed: Int,
    val windDirection: String,
    val precipitation: String,
    val weatherIcon: Int,
)

data class Forecast(
    val date: LocalDate,
    //Icon
    val weatherIcon: Int,
    //Temp high
    val highTemp: Int,
    //Temp low
    val lowTemp: Int,
    //Condition
    val condition: String,
    //Precipitation type
    val precipitationType: String,
    //Precipitation amount
    val precipitationAmount: Double,
    //Precipitation %
    val precipitationProbability: Int,
    //Wind direction
    val windDirection: String,
    //Wind speed
    val windSpeed: Double,
    //humidity
    val humidity: Int
)
