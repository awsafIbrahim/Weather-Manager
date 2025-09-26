package com.example.weathermanager


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathermanager.ui.WeatherViewModel
import com.example.weathermanager.ui.screens.WeatherScreen
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                val vm: WeatherViewModel = viewModel()
                WeatherScreen(viewModel = vm)
            }
        }
    }
}