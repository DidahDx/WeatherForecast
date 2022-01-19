package com.didahdx.weatherforecast.common

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Daniel Didah on 1/19/22
 */
class DateTimeConversionTest {

    @Test
    fun convertToTime() {
        val time=1642563464
        val actualTime="03:16 AM"
        val current=DateTimeConversion.convertToTime(time,10800)
        assertEquals(current,actualTime)
    }
}