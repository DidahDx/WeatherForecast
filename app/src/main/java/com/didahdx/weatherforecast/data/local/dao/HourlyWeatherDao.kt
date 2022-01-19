package com.didahdx.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.didahdx.weatherforecast.data.local.entities.HourlyEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * @author by Daniel Didah on 1/20/22.
 */
@Dao
interface HourlyWeatherDao {

    @Insert(onConflict = REPLACE)
    fun addAllHourlyEntity(hourlyEntity: List<HourlyEntity>):Completable

    @Query("SELECT * FROM HourlyEntity WHERE id <= 23")
    fun getTodayHourlyWeather(): Observable<List<HourlyEntity>>

    @Query("SELECT * FROM HourlyEntity WHERE id > 23")
    fun getTomorrowHourlyWeather(): Observable<List<HourlyEntity>>

    @Query("DELETE FROM HourlyEntity")
    fun deleteAll():Completable
}