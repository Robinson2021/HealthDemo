package com.techeniac.healthsample.ui.launch.splash

import com.techeniac.healthsample.data.DataManager
import com.techeniac.healthsample.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Robinson on 18 Nov 2021
 */
class SplashViewModel @Inject constructor(dataManager: DataManager): BaseViewModel<SplashNavigator>(dataManager) {
}