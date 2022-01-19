package com.didahdx.weatherforecast.data.remote.dto

import com.squareup.moshi.Json

data class Daily(
    val clouds: Int,
    @Json(name = "dew_point")
    val dew_point: Double,
    val dt: Int,
    @Json(name = "feels_like")
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double ,
    val pressure: Int,
    val rain: Double ?,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)