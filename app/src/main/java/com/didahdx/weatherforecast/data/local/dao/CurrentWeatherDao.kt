package com.didahdx.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * @author by Daniel Didah on 1/20/22.
 */
@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = REPLACE)
    fun addCurrent(currentEntity: CurrentEntity): Completable

    @Query("SELECT * FROM CurrentEntity LIMIT 1")
    fun getCurrent(): Observable<CurrentEntity>

    @Query("DELETE FROM CurrentEntity")
    fun deleteAll(): Completable
}