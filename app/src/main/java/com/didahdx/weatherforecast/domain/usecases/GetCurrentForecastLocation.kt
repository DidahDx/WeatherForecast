package com.didahdx.weatherforecast.domain.usecases

import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author Daniel Didah on 1/28/22
 */
class GetCurrentForecastLocation @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) {

    fun getLocation(): Observable<LocationEntity> {
        return weatherForecastRepository.getCurrentLocation()
    }
}