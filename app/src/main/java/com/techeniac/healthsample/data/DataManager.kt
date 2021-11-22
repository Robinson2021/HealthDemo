package com.techeniac.healthsample.data

import com.techeniac.healthsample.data.local.prefs.PreferencesHelper
import com.techeniac.healthsample.data.remote.ApiHelper

/**
 * Created by Robinson on 18 Nov 2021
 */
interface DataManager: PreferencesHelper, ApiHelper {
    enum class LoggedMode constructor(val type: Int) {
        LOGGED_OUT(0),
        LOGGED_IN(1),
    }

}