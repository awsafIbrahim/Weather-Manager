package com.example.weathermanager.data.repository


import com.example.weathermanager.data.model.ForecastResponse
import com.example.weathermanager.data.model.WeatherResponse
import com.example.weathermanager.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class WeatherRepository {
    fun getWeather(city: String, apiKey: String): Flow<Result<WeatherResponse>> = flow {
        try {
            val response = RetrofitInstance.api.getCurrentWeather(city, apiKey)
            emit(Result.success(response))
        } catch (e: IOException) {
            emit(Result.failure(e))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


    fun getForecast(city: String, apiKey: String): Flow<Result<ForecastResponse>> = flow {
        try {
            val response = RetrofitInstance.api.getFiveDayForecast(city, apiKey)
            emit(Result.success(response))
        } catch (e: IOException) {
            emit(Result.failure(e))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}