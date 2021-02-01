package com.test.weather.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    var speed: String? = null,
    var deg: String? = null
) : Parcelable