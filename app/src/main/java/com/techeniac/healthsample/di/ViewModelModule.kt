package com.techeniac.healthsample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techeniac.healthsample.di.annotations.ViewModelKey
import com.techeniac.healthsample.ui.base.ViewModelFactory
import com.techeniac.healthsample.ui.launch.LauncherViewModel
import com.techeniac.healthsample.ui.launch.splash.SplashViewModel
import com.techeniac.healthsample.ui.launch.step.StepViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
/**
 * Created by Robinson on 18 Nov 2021
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindLauncherViewModel(launcherViewModel: LauncherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StepViewModel::class)
    abstract fun bindStepViewModel(mStepViewModel: StepViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}