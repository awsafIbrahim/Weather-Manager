package com.example.weathermanager.data.model


data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main,
    val dt: Long
)


data class Weather(
    val description: String,
    val icon: String
)


data class Main(
    val temp: Double,
    val humidity: Int
)