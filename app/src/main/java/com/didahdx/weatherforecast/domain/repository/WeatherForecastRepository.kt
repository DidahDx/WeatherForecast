package com.didahdx.weatherforecast.domain.repository

import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import io.reactivex.rxjava3.core.Observable

/**
 * @author by Daniel Didah on 1/18/22
 */
interface WeatherForecastRepository {

    fun searchByCityName(cityName: String): Observable<CurrentEntity>

    fun getAllCurrent(): Observable<CurrentEntity>

    fun getCurrentLocation(): Observable<LocationEntity>

    fun clear()
}