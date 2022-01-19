package com.didahdx.weatherforecast.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Daniel Didah on 1/19/22
 */
object DateTimeConversion {
    private const val TIME_FORMAT="hh:mm aaa"

    fun convertToTime(milliseconds:Long):String{
        val date = Date(milliseconds)
        val formatter: DateFormat = SimpleDateFormat(TIME_FORMAT,Locale.getDefault())
       return formatter.format(date)
    }
}