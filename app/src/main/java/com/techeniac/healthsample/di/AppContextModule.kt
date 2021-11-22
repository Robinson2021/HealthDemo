package com.techeniac.healthsample.di

import android.content.Context
import com.techeniac.healthsample.App
import com.techeniac.healthsample.data.AppDataManager
import com.techeniac.healthsample.data.DataManager
import com.techeniac.healthsample.data.local.prefs.AppPreferencesHelper
import com.techeniac.healthsample.data.local.prefs.PreferencesHelper
import com.techeniac.healthsample.di.annotations.PreferenceInfo
import com.techeniac.healthsample.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Robinson on 18 Nov 2021
 */
@Module
class AppContextModule {

    @Provides
    internal fun provideContext(app: App): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return AppConstants.PREFERENCES_NAME
    }
}