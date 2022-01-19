package com.didahdx.weatherforecast.common

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.didahdx.weatherforecast.data.models.LocationData
import com.google.android.gms.common.util.CollectionUtils
import org.apache.commons.lang3.StringUtils
import timber.log.Timber
import java.util.*

/**
 * @author Daniel Didah on 1/19/22
 */

object GeocodeConverter {

    fun getCityName(
        context: Context,
        latitude: String,
        longitude: String,
    ): String? {
        var address = StringUtils.EMPTY
        var cityName = StringUtils.EMPTY
        var areaName = StringUtils.EMPTY

        try {
            val addresses: MutableList<Address>
            val geocoder = Geocoder(context, Locale.getDefault())
            addresses =
                geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            if (!CollectionUtils.isEmpty(addresses)) {
                val fetchedAddress = addresses[0]
                if (fetchedAddress.maxAddressLineIndex > -1) {
                    address = fetchedAddress.getAddressLine(0)
                    fetchedAddress.locality?.let {
                        cityName = it
                    }
                    fetchedAddress.subLocality?.let {
                        areaName = it
                    }
                }

                return cityName
            }
        } catch (e: Exception) {
            Timber.e(e)
        }

        return cityName
    }

    fun getLatLonFromCityName(
        context: Context,
        locationName: String
    ): LocationData? {
        var longitude = StringUtils.EMPTY
        var latitude = StringUtils.EMPTY

        try {
            val addresses: MutableList<Address>
            val geocoder = Geocoder(context, Locale.getDefault())
            addresses = geocoder.getFromLocationName(locationName, 1)

            if (!CollectionUtils.isEmpty(addresses)) {
                val fetchedAddress = addresses[0]
                if (fetchedAddress.maxAddressLineIndex > -1) {
                    longitude= fetchedAddress.longitude.toString()
                    latitude= fetchedAddress.latitude.toString()
                }

                return LocationData(longitude, latitude)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }

        return LocationData(longitude, latitude)
    }
}
