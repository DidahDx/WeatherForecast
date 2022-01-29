package com.didahdx.weatherforecast.data.repository

import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.data.local.dao.CurrentWeatherDao
import com.didahdx.weatherforecast.data.local.dao.DailyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.HourlyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.LocationDao
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.DailyEntity
import com.didahdx.weatherforecast.data.local.entities.HourlyEntity
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import com.didahdx.weatherforecast.data.remote.api.GeocodingApi
import com.didahdx.weatherforecast.data.remote.api.WeatherForecastApi
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by Daniel Didah on 1/18/22
 */
class WeatherForecastRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val weatherForecastApi: WeatherForecastApi,
    private val currentWeatherDao: CurrentWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao,
    private val locationDao: LocationDao
) : WeatherForecastRepository, Disposable {

    private val compositeDisposable = CompositeDisposable()

    override fun searchByCityName(cityName: String): Observable<CurrentEntity> {
        return geocodingApi
            .getGeocoding(cityName, Constants.API_KEY)
            .subscribeOn(Schedulers.io())
            .map {
                it[0]
            }.switchMap { geocodeDetails ->
                compositeDisposable += locationDao.deleteAll().subscribe()
                compositeDisposable += locationDao
                    .addLocation(geocodeDetails.mapToLocationEntity()).subscribe()

                return@switchMap weatherForecastApi
                    .getWeatherForecastByLatitudeLongitude(
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
                currentWeatherDao.getCurrent()
            }
    }


    override fun getCurrentForecast(): Observable<CurrentEntity> {
        return currentWeatherDao.getCurrent()
    }

    override fun getCurrentLocation(): Observable<LocationEntity> {
        return locationDao.getCurrentLocation()
    }

    override fun getTodaysForecast(): Observable<List<HourlyEntity>> {
        return hourlyWeatherDao.getTodayHourlyWeather()
    }

    override fun getTomorrowsForecast(): Observable<List<HourlyEntity>> {
        return hourlyWeatherDao.getTomorrowHourlyWeather()
    }

    override fun getFiveDaysForecast(): Observable<List<DailyEntity>> {
        return dailyWeatherDao.getFirstFiveDailyEntity()
    }


    override fun clear() {

    }

//    @Throws(Throwable::class)
//    protected fun finalize(){
//        compositeDisposable.dispose()
//    }

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun isDisposed(): Boolean {
       return compositeDisposable.isDisposed
    }
}