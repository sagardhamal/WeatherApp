 package com.test.weather.data.db

import androidx.room.*
import com.test.weather.data.db.entity.City


 @Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityName(city: City): Long

    @Query("SELECT * FROM city")
    suspend fun getCityName(): ArrayList<City>


}
