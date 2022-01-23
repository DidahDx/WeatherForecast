package com.didahdx.weatherforecast.domain.usecases.getGeocoding

import com.didahdx.weatherforecast.data.remote.api.GeocodingApi
import com.didahdx.weatherforecast.data.remote.dto.GeocodingDtoItem
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author Daniel Didah on 1/23/22.
 */
class GetGeocodingByCityName @Inject constructor(
    private val geocodingApi: GeocodingApi
) {
    fun getGeocoding(
        cityName: String,
        apiKey: String
    ): Observable<List<GeocodingDtoItem>> {
        return geocodingApi.getGeocoding(cityName, apiKey, 1)
    }
}