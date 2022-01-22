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
        val time: Long= (milliseconds.toLong() * 1000L) + (timeZoneOffSet.toLong() * 1000L)
        val date = Date(time)
        val formatter: DateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }

    fun convertToDateTime(milliseconds: Int,timeZoneOffSet: Int): String {
        val time: Long= (milliseconds.toLong() * 1000L) + (timeZoneOffSet.toLong() * 1000L)
        val date = Date(time)
        val formatter: DateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return formatter.format(date)
    }
}