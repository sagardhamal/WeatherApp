package com.test.weather.data.repository

import android.util.Log
import com.test.weather.data.db.AppDatabase
import com.test.weather.data.db.entity.City


class MainRepository() {
    suspend fun addCity(
        cityEntity: City,
        appDatabase: AppDatabase
    ):Long {
        val id =
            appDatabase.getUserDao().insertCityName(cityEntity)
        Log.d("DB Insert", "" + id)
        return id
    }

    suspend fun getCityName(appDatabase: AppDatabase): ArrayList<City>? {
        var userDbResponse: ArrayList<City> = appDatabase.getUserDao().getCityName()
        return userDbResponse
    }


}