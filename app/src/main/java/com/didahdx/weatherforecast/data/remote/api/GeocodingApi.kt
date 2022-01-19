package com.didahdx.weatherforecast.data.remote.api

import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Daniel Didah on 1/19/22
 */
interface GeocodingApi {

    @GET("direct")
    fun getGeocoding(@Query("q") cityName: String, @Query("appid") apiKey: String, @Query("limit") limit:Int=1): Observable<List<GeocodingDtoItem>>

}