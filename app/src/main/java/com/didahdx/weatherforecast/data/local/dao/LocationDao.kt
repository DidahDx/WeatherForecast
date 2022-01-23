package com.didahdx.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * @author Daniel Didah on 1/23/22.
 */
@Dao
interface LocationDao {
    @Insert(onConflict = REPLACE)
    fun addLocation(locationEntity: LocationEntity):Completable

    @Query("SELECT * FROM LocationEntity Limit 1")
    fun getCurrentLocation():Observable<LocationEntity>

    @Query("DELETE FROM LocationEntity")
    fun deleteAll():Completable
}