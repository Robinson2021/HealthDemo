package com.techeniac.healthsample.ui.launch.step

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Robinson on 18 Nov 2021
 */
@Module
abstract class StepFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideStepFragmentFactory(): StepFragment
}