package com.didahdx.weatherforecast.presentation

/**
 * @author Daniel Didah on 1/18/22
 */
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.didahdx.weatherforecast.di.InjectedSavedStateViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasDefaultViewModelProviderFactory {
    /**
     * dagger.Lazy used here, so that injection is request when [getDefaultViewModelProviderFactory] is called
     */
    @Inject
    lateinit var defaultViewModelFactory: dagger.Lazy<InjectedSavedStateViewModelFactory>

    /**
     * This method androidx uses for `by viewModels` method.
     * We can set out injecting factory here and therefore don't touch it again
     */
    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.get().create(this, arguments)
}
