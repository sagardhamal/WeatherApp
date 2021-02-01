package com.test.weather.ui.home.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.test.weather.data.CityDb
import com.test.weather.data.WeWeekWeather
import com.test.weather.data.db.AppDatabase

import com.test.weather.data.db.entity.City
import com.test.weather.data.repository.MainRepository
import com.test.weather.network.ApiHelper
import com.test.weather.ui.home.list.WeatherListViewModel
import com.test.weather.utils.api.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherMapViewModel(private val apiHelper: ApiHelper?) : ViewModel() {


    private val insertCityId = MutableLiveData<Resource<Long>>()
    private val cityList = MutableLiveData<Resource<ArrayList<CityDb>>>()

    fun saveCity(
        city: City,
        appDatabase: AppDatabase

    ) {
        viewModelScope.launch {
            insertCityId.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val weatherResult: Long =
                        async { MainRepository().addCity(city, appDatabase) }.await()
                    insertCityId.postValue(Resource.success(weatherResult))
                }
            } catch (e: Exception) {
                insertCityId.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getCityList(): LiveData<Resource<ArrayList<CityDb>>> {
        return cityList
    }


    fun getSaveCityNo(): LiveData<Resource<Long>> {
        return insertCityId
    }

}
