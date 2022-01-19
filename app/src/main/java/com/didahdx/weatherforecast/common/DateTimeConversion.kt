package com.didahdx.weatherforecast.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Daniel Didah on 1/19/22
 */
object DateTimeConversion {
    private const val TIME_FORMAT = "hh:mm aaa"
    private const val DATE_TIME_FORMAT = "EEE dd MMM yyyy hh:mm aaa"

    fun convertToTime(milliseconds: Int,timeZoneOffSet:Int): String {
        val time: Int= (milliseconds * 1000).plus(timeZoneOffSet * 1000)
        val date = Date(time.toLong())
        val formatter: DateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    fun convertToDateTime(milliseconds: Int,timeZoneOffSet: Int): String {
        val time: Int= (milliseconds * 1000).plus(timeZoneOffSet * 1000)
        val date = Date(time.toLong())
        val formatter: DateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
}