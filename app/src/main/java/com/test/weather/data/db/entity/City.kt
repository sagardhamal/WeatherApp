package com.test.weather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city")
class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String

)
