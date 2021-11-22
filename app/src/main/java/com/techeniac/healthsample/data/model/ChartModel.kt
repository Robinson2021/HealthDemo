package com.techeniac.healthsample.data.model

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieEntry

data class ChartModel(
    val mLineData: LineData,
    val mHeartLineData: LineData,
    val mBarData: BarData,
    val listPie: ArrayList<PieEntry>,
    val listColors: ArrayList<Int>,
    val days: Array<String>,
    val progressSteps: Int,
    val steps: Int
)