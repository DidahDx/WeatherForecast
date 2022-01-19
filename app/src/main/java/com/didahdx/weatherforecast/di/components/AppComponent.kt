package com.didahdx.weatherforecast.di.components

import android.app.Application
import com.didahdx.weatherforecast.App
import com.didahdx.weatherforecast.di.modules.ApiServiceModule
import com.didahdx.weatherforecast.di.modules.CommonUiModule
import com.didahdx.weatherforecast.di.modules.WeatherDatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author by Daniel Didah on 1/18/22
 */
@Component(
    modules = [ApiServiceModule::class, AppSubComponents::class,
        WeatherDatabaseModule::class, CommonUiModule::class]
)
@Singleton
interface AppComponent {

    fun getActivityComponentFactory(): ActivitySubComponent.Factory
    fun getFragmentComponentFactory(): FragmentSubComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}