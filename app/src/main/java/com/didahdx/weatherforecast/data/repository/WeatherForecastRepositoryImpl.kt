package com.didahdx.weatherforecast.data.repository

import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.data.local.dao.CurrentWeatherDao
import com.didahdx.weatherforecast.data.local.dao.DailyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.HourlyWeatherDao
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.remote.api.GeocodingApi
import com.didahdx.weatherforecast.data.remote.api.WeatherForecastApi
import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author by Daniel Didah on 1/18/22
 */
class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherForecastApi: WeatherForecastApi,
    private val geocodingApi: GeocodingApi,
    private val currentWeatherDao: CurrentWeatherDao,
    private val dailyWeatherDao: DailyWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao
) : WeatherForecastRepository {

    val compositeDisposable= CompositeDisposable()
    override fun getWeatherForecastByCity(
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

    override fun getGeocoding(
        cityName: String,
        apiKey: String
    ): Observable<List<GeocodingDtoItem>> {
        return geocodingApi.getGeocoding(cityName, apiKey, 1)
    }

    override fun searchByCityName(cityName: String): Observable<CurrentEntity> {
        return getGeocoding(cityName, Constants.API_KEY)
            .subscribeOn(Schedulers.io())
            .map {
                it[0]
            }.switchMap { geocodeDetails ->
                return@switchMap getWeatherForecastByCity(
                    geocodeDetails.lat.toString(),
                    geocodeDetails.lon.toString(),
                    "",
                    Constants.API_KEY
                )
            }.switchMap { oneCallWeather ->
               compositeDisposable += currentWeatherDao.deleteAll().subscribe()
                compositeDisposable +=  dailyWeatherDao.deleteAll().subscribe()
                compositeDisposable += hourlyWeatherDao.deleteAll().subscribe()
                compositeDisposable +=  currentWeatherDao.addCurrent(oneCallWeather.current.mapToCurrentEntity(oneCallWeather.timezone_offset)).subscribe()
                compositeDisposable +=  dailyWeatherDao.addAllDailyEntity(oneCallWeather.daily.map { it.mapToDailyEntity(oneCallWeather.timezone_offset) }).subscribe()
                compositeDisposable +=  hourlyWeatherDao.addAllHourlyEntity(oneCallWeather.hourly.map{it.mapToHourlyEntity(oneCallWeather.timezone_offset)}).subscribe()
                currentWeatherDao.getAllCurrent()
            }
    }

    override fun searchByLatLong(
        latitude: String,
        longitude: String
    ): Observable<OneCallWeatherForecast> {
        return getWeatherForecastByCity(latitude, longitude, "", Constants.API_KEY)
    }


    override fun clear(){
        compositeDisposable.dispose()
    }
}