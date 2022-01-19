package com.didahdx.weatherforecast.data.repository

import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.data.remote.api.GeocodingApi
import com.didahdx.weatherforecast.data.remote.api.WeatherForecastApi
import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by Daniel Didah on 1/18/22
 */
class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherForecastApi: WeatherForecastApi,
    private val geocodingApi: GeocodingApi
) : WeatherForecastRepository {


    override fun getWeatherForecastByCity(
        latitude: String,
        longitude: String,
        exclude: String,
        apiKey: String
    ): Observable<OneCallWeatherForecast> {
        return weatherForecastApi.getWeatherForecastByLatitudeLongitude(latitude, longitude, exclude, apiKey)
    }

    override fun getGeocoding(cityName: String, apiKey: String): Observable<List<GeocodingDtoItem>> {
        return geocodingApi.getGeocoding(cityName, apiKey,1)
    }

    override fun searchByCityName(cityName: String): Observable<OneCallWeatherForecast> {
        return getGeocoding(cityName, Constants.API_KEY)
            .subscribeOn(Schedulers.io())
            .map {
                it[0]
            }.switchMap { geocodeDetails ->
                return@switchMap getWeatherForecastByCity(geocodeDetails.lat.toString()
                    ,geocodeDetails.lon.toString(),"",Constants.API_KEY)

            }
    }

    override fun searchByLatLong(
        latitude: String,
        longitude: String
    ): Observable<OneCallWeatherForecast> {
        return getWeatherForecastByCity(latitude,longitude,"",Constants.API_KEY)
    }



}