package com.techeniac.healthsample.data

import com.techeniac.healthsample.data.local.prefs.PreferencesHelper
import com.techeniac.healthsample.data.remote.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Robinson on 18 Nov 2021
 */
@Singleton
class AppDataManager @Inject constructor(private val api: ApiHelper, private val preferencesHelper: PreferencesHelper) :
    DataManager {

    override fun setIntValue(value: Int) {
        preferencesHelper.setIntValue(value)
    }

    override fun getIntValue(): Int {
        return preferencesHelper.getIntValue()
    }

    override fun step(req:String) = api.step(req)
}