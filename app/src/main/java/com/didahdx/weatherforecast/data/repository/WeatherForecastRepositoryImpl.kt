package com.didahdx.weatherforecast.data.repository

import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.data.local.dao.CurrentWeatherDao
import com.didahdx.weatherforecast.data.local.dao.DailyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.HourlyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.LocationDao
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import com.didahdx.weatherforecast.domain.usecases.getGeocoding.GetGeocodingByCityName
import com.didahdx.weatherforecast.domain.usecases.getWeatherForecastByCity.GetWeatherForecastByCityLatLong
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by Daniel Didah on 1/18/22
 */
class WeatherForecastRepositoryImpl @Inject constructor(
    private val getWeatherForecastByCityLatLong: GetWeatherForecastByCityLatLong,
    private val getGeocodingByCityName: GetGeocodingByCityName,
    private val currentWeatherDao: CurrentWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val locationDao: LocationDao
) : WeatherForecastRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun searchByCityName(cityName: String): Observable<CurrentEntity> {
        return getGeocodingByCityName
            .getGeocoding(cityName, Constants.API_KEY)
            .subscribeOn(Schedulers.io())
            .map {
                it[0]
            }.switchMap { geocodeDetails ->
                compositeDisposable += locationDao.deleteAll().subscribe()
                compositeDisposable += locationDao
                    .addLocation(geocodeDetails.mapToLocationEntity()).subscribe()

                return@switchMap getWeatherForecastByCityLatLong
                    .getWeatherForecast(
                    geocodeDetails.lat.toString(),
                    geocodeDetails.lon.toString(),
                    "",
                    Constants.API_KEY
                )
            }.switchMap { oneCallWeather ->
                compositeDisposable += currentWeatherDao.deleteAll().subscribe()
                compositeDisposable += dailyWeatherDao.deleteAll().subscribe()
                compositeDisposable += hourlyWeatherDao.deleteAll().subscribe()
                compositeDisposable += currentWeatherDao.addCurrent(
                    oneCallWeather.current.mapToCurrentEntity(
                        oneCallWeather.timezoneOffset
                    )
                ).subscribe()
                compositeDisposable += dailyWeatherDao.addAllDailyEntity(oneCallWeather.daily.map {
                    it.mapToDailyEntity(
                        oneCallWeather.timezoneOffset
                    )
                }).subscribe()
                compositeDisposable += hourlyWeatherDao.addAllHourlyEntity(oneCallWeather.hourly.map {
                    it.mapToHourlyEntity(
                        oneCallWeather.timezoneOffset
                    )
                }).subscribe()
                currentWeatherDao.getAllCurrent()
            }
    }


    override fun getAllCurrent(): Observable<CurrentEntity> {
        return currentWeatherDao.getAllCurrent()
    }

    override fun getCurrentLocation(): Observable<LocationEntity> {
        return locationDao.getCurrentLocation()
    }


    override fun clear() {
        compositeDisposable.dispose()
    }
}