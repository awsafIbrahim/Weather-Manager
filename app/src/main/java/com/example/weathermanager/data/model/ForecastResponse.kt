package com.example.weathermanager.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "list") val list: List<ForecastItem>
) {
    @JsonClass(generateAdapter = true)
    data class ForecastItem(
        @Json(name = "dt_txt") val dt_txt: String,
        @Json(name = "main") val main: Main,
        @Json(name = "weather") val weather: List<Weather>
    )

    @JsonClass(generateAdapter = true)
    data class Main(
        @Json(name = "temp") val temp: Double
    )

    @JsonClass(generateAdapter = true)
    data class Weather(
        @Json(name = "description") val description: String
    )
}
