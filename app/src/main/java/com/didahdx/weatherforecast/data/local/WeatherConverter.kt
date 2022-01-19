package com.didahdx.weatherforecast.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.didahdx.weatherforecast.data.remote.dto.Weather
import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


/**
 * @author by Daniel Didah on 1/20/22.
 */
class WeatherConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter: JsonAdapter<Weather> = moshi.adapter(Weather::class.java)

    @TypeConverter
    fun fromWeather(weather: Weather):String{
        return jsonAdapter.toJson(weather)
    }

    @TypeConverter
    fun toWeather(weather:String):Weather?{
        return jsonAdapter.fromJson(weather)
    }
}