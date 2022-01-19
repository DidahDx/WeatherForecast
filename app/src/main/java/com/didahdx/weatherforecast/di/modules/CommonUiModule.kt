package com.didahdx.weatherforecast.di.modules

import androidx.lifecycle.ViewModel
import com.didahdx.weatherforecast.di.AssistedSavedStateViewModelFactory
import dagger.Module
import dagger.multibindings.Multibinds

/**
 * @author by Daniel Didah on 1/18/22
 */
@Module
abstract class CommonUiModule {
    @Multibinds
    abstract fun viewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards ViewModel>

    @Multibinds
    abstract fun assistedViewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedSavedStateViewModelFactory<out ViewModel>>
}