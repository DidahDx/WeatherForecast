package com.didahdx.weatherforecast.presentation.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.data.remote.dto.Current
import com.didahdx.weatherforecast.di.AssistedSavedStateViewModelFactory
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class CurrentWeatherForecastViewModel @AssistedInject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val compositeDisposable=CompositeDisposable()
    val currentWeather=MutableLiveData<Current>()

    init {
      search("Nairobi")
    }

    private fun search(cityName:String){
      compositeDisposable.add(weatherForecastRepository.searchByCityName(cityName)
          .subscribeOn(Schedulers.io())
          .subscribe({
              Timber.e("$it")
              currentWeather.postValue(it.current)
          }, Timber::e)
      )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun initialiseCurrentCity(cityName: String){
        Timber.e(cityName)
        search(cityName)
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<CurrentWeatherForecastViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CurrentWeatherForecastViewModel
    }
}