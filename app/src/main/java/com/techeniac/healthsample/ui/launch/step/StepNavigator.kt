package com.techeniac.healthsample.ui.launch.step

import android.app.Activity
import android.content.Context

/**
 * Created by Robinson on 18 Nov 2021
 */
interface StepNavigator {

    fun activity(): Activity

    fun context(): Context

}