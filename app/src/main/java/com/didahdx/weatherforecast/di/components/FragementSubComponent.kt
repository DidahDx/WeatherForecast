package com.didahdx.weatherforecast.di.components

import com.didahdx.weatherforecast.di.FragmentScope
import com.didahdx.weatherforecast.di.modules.ViewModelModule
import com.didahdx.weatherforecast.presentation.forecast.CurrentWeatherForecastFragment
import dagger.Subcomponent

/**
 * @author by Daniel Didah on 1/18/22
 */

@FragmentScope
@Subcomponent(modules = [ViewModelModule::class])
interface FragmentSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentSubComponent
    }

    fun inject(currentWeatherForecastFragment: CurrentWeatherForecastFragment)

}