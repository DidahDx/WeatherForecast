package com.didahdx.weatherforecast.data.remote.api

import com.didahdx.weatherforecast.data.remote.dto.OneCallWeatherForecast
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author by Daniel Didah on 1/18/22
 */
interface WeatherForecastApi {


    @GET("onecall")
    fun getWeatherForecastByLatitudeLongitude(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String = "metric",
    ): Observable<OneCallWeatherForecast>

    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String,
                                 @Query("appid") apiKey: String)


}