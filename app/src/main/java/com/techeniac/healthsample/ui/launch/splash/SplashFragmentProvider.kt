package com.techeniac.healthsample.ui.launch.splash

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Robinson on 18 Nov 2021
 */
@Module
abstract class SplashFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideSplashFragmentFactory(): SplashFragment
}