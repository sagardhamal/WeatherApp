package com.test.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.weather.network.ApiHelper
import com.test.weather.ui.home.list.WeatherListViewModel

class ViewModelFactory(private val apiHelper: ApiHelper?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherListViewModel::class.java)) {
            return WeatherListViewModel(apiHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}