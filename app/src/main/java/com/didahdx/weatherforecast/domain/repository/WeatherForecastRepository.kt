package com.didahdx.weatherforecast.domain.repository

import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.DailyEntity
import com.didahdx.weatherforecast.data.local.entities.HourlyEntity
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * @author by Daniel Didah on 1/18/22
 */
interface WeatherForecastRepository {

    fun searchByCityName(cityName: String): Completable

    fun getCurrentForecast(): Observable<CurrentEntity>

    fun getCurrentLocation(): Observable<LocationEntity>

    fun getTodaysForecast(): Observable<List<HourlyEntity>>

    fun getTomorrowsForecast(): Observable<List<HourlyEntity>>

    fun getFiveDaysForecast(): Observable<List<DailyEntity>>

}