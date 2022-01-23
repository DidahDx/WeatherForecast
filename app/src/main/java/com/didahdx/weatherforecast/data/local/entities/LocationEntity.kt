package com.didahdx.weatherforecast.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Daniel Didah on 1/23/22.
 */
@Entity
data class LocationEntity(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
