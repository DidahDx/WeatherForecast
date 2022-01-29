package com.didahdx.weatherforecast.presentation.forecast

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.data.local.entities.CurrentEntity
import com.didahdx.weatherforecast.data.local.entities.LocationEntity
import com.didahdx.weatherforecast.di.AssistedSavedStateViewModelFactory
import com.didahdx.weatherforecast.domain.usecases.GetCurrentForecast
import com.didahdx.weatherforecast.domain.usecases.GetCurrentForecastLocation
import com.didahdx.weatherforecast.domain.usecases.SearchForecast
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit


class CurrentWeatherForecastViewModel @AssistedInject constructor(
    private val getCurrentForecast: GetCurrentForecast,
    private val getCurrentForecastLocation: GetCurrentForecastLocation,
    private val searchForecast: SearchForecast,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val SEARCH_CITY = "SEARCH_CITY"
    private val compositeDisposable = CompositeDisposable()
    val currentWeather = MutableLiveData<CurrentEntity>()
    val currentSearch = MutableLiveData<LocationEntity>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    var searchMutableLiveData: MutableLiveData<String> =
        savedStateHandle.getLiveData(SEARCH_CITY, "")
    private val searchPublishSubject: PublishSubject<String> = PublishSubject.create<String>()
    var isDefaultSearchSet = false

    init {
        initRxSearch()
        compositeDisposable.add(
            getCurrentForecast.getCurrentForecast()
                .subscribeOn(Schedulers.io())
                .subscribe(currentWeather::postValue, Timber::e)
        )
        compositeDisposable += getCurrentForecastLocation.getLocation()
            .subscribeOn(Schedulers.io())
            .subscribe(currentSearch::postValue, Timber::e)
    }

    fun search(cityName: String) {
        Timber.e(cityName)
        searchMutableLiveData.value = cityName
        searchPublishSubject.onNext(cityName)
    }

    private fun initRxSearch() {
        compositeDisposable.add(
            searchPublishSubject
                .subscribeOn(Schedulers.io())
                .throttleLast(300, TimeUnit.MILLISECONDS)
                .filter { !TextUtils.isEmpty(it) }
                .doOnNext {
                    isLoading.postValue(true)
                }
                .switchMap {
                    Timber.e(it)
                    return@switchMap searchForecast.byCityNameForecast(it)
                        .andThen(Observable.just(Unit))
                        .onErrorComplete { error ->
                                Timber.e(error)
                                errorMessage.postValue(error.localizedMessage)
                                isLoading.postValue(false)
                                true
                            }
                }.subscribe({
                    isLoading.postValue(false)
                }, { error ->
                    Timber.e(error)
                    errorMessage.postValue(error.localizedMessage)
                    isLoading.postValue(false)
                })
//                .flatMapCompletable {
//                    Timber.e(it)
//
//                        return@flatMapCompletable searchForecast.byCityNameForecast(it)
//                            .onErrorComplete { error ->
//                                Timber.e(error)
//                                errorMessage.postValue(error.localizedMessage)
//                                isLoading.postValue(false)
//                                true
//                            }.doOnComplete {
//                                isLoading.postValue(false)
//                            }
//                }
//                .subscribeBy({
//                    Timber.e("$it")
//                    Timber.e(it)
//                    isLoading.postValue(false)
//                    errorMessage.postValue(it.localizedMessage)
//                }, onComplete = {
//                    isLoading.postValue(false)
//                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun initialiseCurrentCity(cityName: String) {
        if (isDefaultSearchSet) return
        Timber.e(cityName)
        search(cityName)
        isDefaultSearchSet = true
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<CurrentWeatherForecastViewModel> {
        override fun create(savedStateHandle: SavedStateHandle): CurrentWeatherForecastViewModel
    }
}