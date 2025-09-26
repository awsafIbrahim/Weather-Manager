package com.example.weathermanager.ui.screens
import com.example.weathermanager.data.model.ForecastResponse
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.weathermanager.data.model.WeatherResponse
import com.example.weathermanager.ui.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf(TextFieldValue("")) }
    val state = viewModel.weather.collectAsState()
    val forecastState = viewModel.forecast.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLastCity { last ->
            if (!last.isNullOrBlank()) {
                city = TextFieldValue(last)
                viewModel.fetchWeather(last)
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { if (city.text.isNotBlank()) viewModel.fetchWeather(city.text.trim()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(Modifier.height(24.dp))

        // Weather result
        when (val result = state.value) {
            null -> Text("Enter a city to begin.")
            else -> {
                result.onSuccess { data ->
                    WeatherCard(data)
                }.onFailure { e ->
                    Text("Error: ${e.message ?: "Unknown error"}")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Forecast result
        when (val forecastResult = forecastState.value) {
            null -> {}
            else -> {
                forecastResult.onSuccess { forecast ->
                    ForecastCard(forecast.list.take(5)) // Show next 5 entries
                }.onFailure { e ->
                    Text("Forecast error: ${e.message ?: "Unknown error"}")
                }
            }
        }
    }
}

@Composable
private fun WeatherCard(data: WeatherResponse?) {
    if (data == null) return

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(text = data.name, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))
            Text(text = "Temperature: ${data.main.temp} °C")
            Text(text = "Humidity: ${data.main.humidity}%")
            Text(text = "Condition: ${data.weather.firstOrNull()?.description ?: "-"}")
        }
    }
}

@Composable
private fun ForecastCard(items: List<ForecastResponse.ForecastItem>) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Forecast (Next 5 Days):", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))
            for (item in items) {
                Text("${item.dt_txt} → ${item.main.temp}°C, ${item.weather.firstOrNull()?.description ?: ""}")
            }
        }
    }
}
