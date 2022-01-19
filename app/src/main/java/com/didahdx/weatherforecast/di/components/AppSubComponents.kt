package com.didahdx.weatherforecast.di.components

import dagger.Module

/**
 * @author by Daniel Didah on 1/18/22
 */
@Module(
    subcomponents = [
        ActivitySubComponent::class,
        FragmentSubComponent::class
    ]
)
class AppSubComponents