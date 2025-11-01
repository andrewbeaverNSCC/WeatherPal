package com.example.weatherpal.services
import com.example.weatherpal.models.Weather
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): Weather
}