package com.techeniac.healthsample.utils

/**
 * Created by Robinson on 18 Nov 2021
 */
class AppConstants {
    companion object {
        const val SPLASH_TIME = 1500L

        var progressSteps = 8000

        var lineChartStartRange = 1000
        var lineChartEndRange = 3000

        var barChartStartRange = 3000
        var barChartEndRange = 8000

        var heartRateEndRange = 200


        var totalSteps = 0

        const val PREFERENCES_NAME = "health_preferences"

        fun randomCount(end: Int): Int {
            return (0..end).random()
        }

        fun randomCount(start: Int, end: Int): Int {
            return (start..end).random()
        }
    }


}