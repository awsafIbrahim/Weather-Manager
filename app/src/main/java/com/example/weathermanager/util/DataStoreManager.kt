package com.example.weathermanager.util


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private val Context.dataStore by preferencesDataStore("weather_prefs")


object DataStoreKeys {
    val LAST_CITY = stringPreferencesKey("last_city")
}


class DataStoreManager(private val context: Context) {
    suspend fun saveLastCity(city: String) {
        context.dataStore.edit { it[DataStoreKeys.LAST_CITY] = city }
    }
    suspend fun getLastCity(): String? {
        return context.dataStore.data.first()[DataStoreKeys.LAST_CITY]
    }
}