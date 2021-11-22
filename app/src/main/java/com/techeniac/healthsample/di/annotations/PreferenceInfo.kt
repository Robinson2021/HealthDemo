package com.techeniac.healthsample.di.annotations

import javax.inject.Qualifier
import kotlin.annotation.Retention

/**
 * Created by Robinson on 18 Nov 2021
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PreferenceInfo {
}