package com.didahdx.weatherforecast.domain.repository

import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import io.reactivex.rxjava3.core.Observable

/**
 * @author by Daniel Didah on 1/18/22
 */
interface WeatherForecastRepository {

    fun getWeatherForecastByCity(
        latitude: String, longitude: String, exclude: String,
        apiKey: String
    ): Observable<OneCallWeatherForecast>

    fun getGeocoding(cityName: String, apiKey: String): Observable<List<GeocodingDtoItem>>

    fun searchByCityName(cityName: String): Observable<CurrentEntity>

    fun searchByLatLong(latitude: String, longitude: String): Observable<OneCallWeatherForecast>
}