package com.didahdx.weatherforecast.domain.usecases.getWeatherForecastByCity

import com.didahdx.weatherforecast.data.remote.api.WeatherForecastApi
import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author Daniel Didah on 1/18/22
 */
class GetWeatherForecastByCityLatLong @Inject constructor(
    private val weatherForecastApi: WeatherForecastApi
){
    fun getWeatherForecast(
        latitude: String,
        longitude: String,
        exclude: String,
        apiKey: String
    ): Observable<OneCallWeatherForecast> {
        return weatherForecastApi.getWeatherForecastByLatitudeLongitude(
            latitude,
            longitude,
            exclude,
            apiKey
        )
    }
}