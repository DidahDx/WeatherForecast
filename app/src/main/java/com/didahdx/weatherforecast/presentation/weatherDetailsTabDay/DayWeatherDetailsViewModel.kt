package com.didahdx.weatherforecast.presentation.weatherDetailsTabDay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.data.local.dao.DailyWeatherDao
import com.didahdx.weatherforecast.data.local.dao.HourlyWeatherDao
import com.didahdx.weatherforecast.data.local.entities.DailyEntity
import com.didahdx.weatherforecast.data.local.entities.HourlyEntity
import com.didahdx.weatherforecast.presentation.forecast.WeatherTabTypes
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DayWeatherDetailsViewModel @Inject constructor(
    private val dailyWeatherDao: DailyWeatherDao,
    private val hourlyWeatherDao: HourlyWeatherDao
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val dailyWeather = MutableLiveData<List<DailyEntity>>()
    val hourlyWeather = MutableLiveData<List<HourlyEntity>>()

    fun setWeatherTabType(weatherTabTypes: WeatherTabTypes) {
        when (weatherTabTypes) {
            WeatherTabTypes.TODAY -> {
                compositeDisposable.add(
                    hourlyWeatherDao.getTodayHourlyWeather()
                        .subscribeOn(Schedulers.io())
                        .subscribe(hourlyWeather::postValue, Timber::e)
                )
            }
            WeatherTabTypes.TOMORROW -> {
                compositeDisposable.add(
                    hourlyWeatherDao.getTomorrowHourlyWeather()
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                           Collections.reverse(it)
                            hourlyWeather.postValue(it)
                        }, Timber::e)
                )
            }
            WeatherTabTypes.LATTER -> {
                compositeDisposable.add(
                    dailyWeatherDao.getFirstFiveDailyEntity()
                        .subscribeOn(Schedulers.io())
                        .subscribe(dailyWeather::postValue, Timber::e)
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}