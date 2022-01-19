package com.didahdx.weatherforecast.presentation.forecast

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.didahdx.weatherforecast.presentation.weatherDetailsTabDay.DayWeatherDetailsFragment

/**
 * @author Daniel Didah on 1/18/22
 */
class ForecastPageAdapter(fragment: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
       return WeatherTabTypes.values().size
    }

    override fun createFragment(position: Int): Fragment {
       val weatherTabTypes = WeatherTabTypes.values()[position]
        return DayWeatherDetailsFragment.newInstance(weatherTabTypes)
    }
}