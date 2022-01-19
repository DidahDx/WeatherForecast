package com.didahdx.weatherforecast.di.modules

import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.di.AssistedSavedStateViewModelFactory
import com.didahdx.weatherforecast.di.ViewModelKey
import com.didahdx.weatherforecast.presentation.forecast.CurrentWeatherForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author by Daniel Didah on 1/18/22
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherForecastViewModel::class)
    abstract fun bindMainActivityViewModel(homeViewModel: CurrentWeatherForecastViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>


}