package com.didahdx.weatherforecast.data.remote.dto

import com.squareup.moshi.Json

data class OneCallWeatherForecast(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @Json(name = "timezone_offset")
    val timezoneOffset: Int
)