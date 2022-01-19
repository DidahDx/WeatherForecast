package com.didahdx.weatherforecast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.didahdx.weatherforecast.data.local.dao.CurrentWeatherDao
import com.didahdx.weatherforecast.data.local.dao.DailyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.HourlyWeatherDao
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.DailyEntity
import com.didahdx.weatherforecast.data.local.entities.HourlyEntity

/**
 * @author by Daniel Didah on 1/20/22.
 */
@Database(
    entities = [CurrentEntity::class, DailyEntity::class, HourlyEntity::class],
    version = 1
)
@TypeConverters(WeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getCurrentWeatherDao(): CurrentWeatherDao
    abstract fun getDailyWeatherDao(): DailyWeatherDao
    abstract fun getHourlyWeatherDao(): HourlyWeatherDao
}