package com.example.weathermanager.ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathermanager.BuildConfig
import com.example.weathermanager.data.model.ForecastResponse
import com.example.weathermanager.data.model.WeatherResponse
import com.example.weathermanager.data.repository.WeatherRepository
import com.example.weathermanager.util.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class WeatherViewModel(app: Application) : AndroidViewModel(app) {


    private val repository = WeatherRepository()
    private val dataStore = DataStoreManager(app)


    private val _weather = MutableStateFlow<Result<WeatherResponse>?>(null)
    val weather: StateFlow<Result<WeatherResponse>?> = _weather


    private val _forecast = MutableStateFlow<Result<ForecastResponse>?>(null)
    val forecast: StateFlow<Result<ForecastResponse>?> = _forecast


    fun fetchWeather(city: String) {
        viewModelScope.launch {
            dataStore.saveLastCity(city)
            repository.getWeather(city, BuildConfig.OPEN_WEATHER_API_KEY).collect {
                _weather.value = it
            }
            repository.getForecast(city, BuildConfig.OPEN_WEATHER_API_KEY).collect {
                _forecast.value = it
            }
        }
    }


    fun loadLastCity(onLoaded: (String?) -> Unit) {
        viewModelScope.launch {
            onLoaded(dataStore.getLastCity())
        }
    }
}