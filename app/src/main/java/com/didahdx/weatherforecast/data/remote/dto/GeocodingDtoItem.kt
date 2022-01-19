package com.didahdx.weatherforecast.data.remote.dto

data class GeocodingDtoItem(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)