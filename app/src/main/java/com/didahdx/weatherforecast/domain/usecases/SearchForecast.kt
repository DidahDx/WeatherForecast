package com.didahdx.weatherforecast.domain.usecases

import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author Daniel Didah on 1/28/22
 */
class SearchForecast @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) {

    fun byCityNameForecast(cityName: String): Completable {
        return weatherForecastRepository.searchByCityName(cityName)
    }

}