package com.techeniac.healthsample.di

import com.techeniac.healthsample.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Robinson on 18 Nov 2021
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, NetworkModule::class, AppContextModule::class, BuilderModule::class, ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}