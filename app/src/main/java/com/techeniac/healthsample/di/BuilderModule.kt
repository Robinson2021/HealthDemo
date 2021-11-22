package com.techeniac.healthsample.di

import com.techeniac.healthsample.ui.launch.LauncherActivity
import com.techeniac.healthsample.ui.launch.LauncherActivityModule
import com.techeniac.healthsample.ui.launch.splash.SplashFragmentProvider
import com.techeniac.healthsample.ui.launch.step.StepFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Robinson on 18 Nov 2021
 */
@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(
        modules =
        [LauncherActivityModule::class,
            SplashFragmentProvider::class,
            StepFragmentProvider::class]
    )
    internal abstract fun bindLauncherActivity(): LauncherActivity
}