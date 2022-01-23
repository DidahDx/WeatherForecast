package com.didahdx.weatherforecast.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.didahdx.weatherforecast.data.remote.dto.Weather

/**
 * @author by Daniel Didah on 1/20/22.
 */
@Entity
data class CurrentEntity(
    val dt: Int,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val weather: Weather?,
    val windSpeed: Double,
    val timezoneOffSet: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
