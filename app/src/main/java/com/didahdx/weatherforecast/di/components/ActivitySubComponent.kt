package com.didahdx.weatherforecast.di.components

import com.didahdx.weatherforecast.di.ActivityScope
import com.didahdx.weatherforecast.presentation.MainActivity
import dagger.Subcomponent

/**
 * @author by Daniel Didah on 1/18/22
 */
@ActivityScope
@Subcomponent()
interface ActivitySubComponent {

    // Factory that is used to create instances of this subcomponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivitySubComponent
    }

    fun inject(mainActivity: MainActivity)

}