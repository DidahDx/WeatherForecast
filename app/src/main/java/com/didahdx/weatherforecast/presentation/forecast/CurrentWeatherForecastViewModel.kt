package com.didahdx.weatherforecast.presentation.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.data.local.dao.CurrentWeatherDao
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.remote.dto.Current
import com.didahdx.weatherforecast.di.AssistedSavedStateViewModelFactory
import com.didahdx.weatherforecast.domain.repository.WeatherForecastRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import io.reactivex.rxjava3.subjects.PublishSubject




class CurrentWeatherForecastViewModel @AssistedInject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val currentWeatherDao: CurrentWeatherDao,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val currentWeather = MutableLiveData<CurrentEntity>()
    private val searchPublishSubject: PublishSubject<String> = PublishSubject.create<String>()

    init {
        initRxSearch()
        compositeDisposable.add(
            currentWeatherDao.getAllCurrent()
                .subscribeOn(Schedulers.io())
                .subscribe(currentWeather::postValue,Timber::e)
        )
    }

     fun search(cityName: String) {
       searchPublishSubject.onNext(cityName)
    }

    private fun initRxSearch(){
        compositeDisposable.add(
            searchPublishSubject
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .switchMap{
                    weatherForecastRepository.searchByCityName(it)
                }
                .subscribe({
                    Timber.e("$it")
//                    currentWeather.postValue(it)
                }, Timber::e)
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        weatherForecastRepository.clear()
    }

    fun initialiseCurrentCity(cityName: String) {
        Timber.e(cityName)
        search(cityName)
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<CurrentWeatherForecastViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CurrentWeatherForecastViewModel
    }
}