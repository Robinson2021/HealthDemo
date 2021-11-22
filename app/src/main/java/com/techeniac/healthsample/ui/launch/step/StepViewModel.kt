package com.techeniac.healthsample.ui.launch.step

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.techeniac.healthsample.R
import com.techeniac.healthsample.data.DataManager
import com.techeniac.healthsample.data.model.ChartModel
import com.techeniac.healthsample.ui.base.BaseViewModel
import com.techeniac.healthsample.utils.AppConstants
import javax.inject.Inject


/**
 * Created by Robinson on 18 Nov 2021
 */
class StepViewModel @Inject constructor(dataManager: DataManager) :
    BaseViewModel<StepNavigator>(dataManager) {

//    fun step() {
//        displayLoader(true)
//        dataManager.step("req")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : ApiObserver<Any>(compositeDisposable) {
//                override fun onSuccess(data: Any) {
//                    displayLoader(false)
//
//                }
//            })
//    }

    private val mCombinedChartLiveData = MutableLiveData<ChartModel>()
    private val mStepProgressLiveData = MutableLiveData<Int>()

    fun getStepProgress(steps: Int): LiveData<Int> {
        if (steps != 0) {
            AppConstants.progressSteps = AppConstants.progressSteps + steps
        }

        mStepProgressLiveData.value = AppConstants.progressSteps
        return mStepProgressLiveData

    }


    /**
     *  LineData, BarData, PieEntry
     *  set dummy data in charts
     */

    fun getChartData(context: Context, type: Int): LiveData<ChartModel> {
        val barChartLabelArray: Array<String>
        when (type) {
            1 -> {
                barChartLabelArray = arrayOf(
                    "Sun"
                )
            }
            2 -> {
                barChartLabelArray = arrayOf(
                    "Sun",
                    "Mon",
                    "Tue",
                    "Wed",
                    "Thu",
                    "Fri",
                    "Sat"
                )
            }
            else -> {
                barChartLabelArray = arrayOf(
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "Jun",
                    "Jul",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec"
                )
            }
        }
        // LineData
        val mLineData = LineData()
        val entries = ArrayList<Entry>()

        for (index in barChartLabelArray.indices) {
            val steps = AppConstants.randomCount(AppConstants.lineChartStartRange, AppConstants.lineChartEndRange)
            entries.add(
                Entry(
                    index.toFloat(),
                    steps.toFloat()
                )
            )
        }
        val mLineDataSet = LineDataSet(entries, context.getString(R.string.runtime))
        mLineDataSet.color = ContextCompat.getColor(context, R.color.run)
        mLineDataSet.setDrawValues(true)
        mLineDataSet.valueTextColor = ContextCompat.getColor(context, R.color.black)
        mLineDataSet.lineWidth = 5f
        mLineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.red))
        // mLineDataSet.setCircleColor(Color.rgb(240, 238, 70))

        // mLineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        mLineDataSet.valueTextSize = 10f
        mLineDataSet.lineWidth = 2F
        mLineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        mLineData.addDataSet(mLineDataSet)


        // BarData
        val mBarData = BarData()
        val barEntries: ArrayList<BarEntry> = ArrayList()
        AppConstants.totalSteps = 0
        for (index in barChartLabelArray.indices) {
            val steps = AppConstants.randomCount(AppConstants.barChartStartRange, AppConstants.barChartEndRange)
            AppConstants.totalSteps = AppConstants.totalSteps + steps
            barEntries.add(
                BarEntry(
                    index.toFloat(),
                    steps.toFloat()
                )
            )
        }

        val mBarDataSet = BarDataSet(barEntries, context.getString(R.string.steps))
        //  mBarDataSet.axisDependency = YAxis.AxisDependency.LEFT
        mBarDataSet.color = ContextCompat.getColor(context, R.color.colorPrimary)
        mBarDataSet.valueTextColor = ContextCompat.getColor(context, R.color.climb)
        mBarDataSet.valueTextSize = 12f
        val barWidth = 0.9f
        mBarData.barWidth = barWidth
        mBarData.addDataSet(mBarDataSet)

        // BarData
        val mHeartLineData = LineData()
        val heartEntries = ArrayList<Entry>()
        for (index in 0 until 10) {
            heartEntries.add(Entry(index.toFloat(), AppConstants.randomCount(AppConstants.heartRateEndRange).toFloat()))
        }
        val set = LineDataSet(heartEntries, context.getString(R.string.heart_rate))
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = ContextCompat.getColor(context, R.color.red)
        set.valueTextColor = ContextCompat.getColor(context, R.color.white)
        set.lineWidth = 3f
        set.setDrawCircles(false)
        set.isHighlightEnabled = false
        set.setDrawValues(false)
        set.mode = LineDataSet.Mode.CUBIC_BEZIER
        set.cubicIntensity = 0.3f
        mHeartLineData.addDataSet(set)


        //Pie label and value
        val listPie = ArrayList<PieEntry>()
        listPie.add(PieEntry(60F, context.getString(R.string.jog)))
        listPie.add(PieEntry(20F, context.getString(R.string.run)))
        listPie.add(PieEntry(10F, context.getString(R.string.climb)))

        //Pie list colors
        val listColors = ArrayList<Int>()
        listColors.add(ContextCompat.getColor(context, R.color.jog))
        listColors.add(ContextCompat.getColor(context, R.color.run))
        listColors.add(ContextCompat.getColor(context, R.color.climb))

        mCombinedChartLiveData.value =
            (ChartModel(
                mLineData,
                mHeartLineData,
                mBarData,
                listPie,
                listColors,
                barChartLabelArray,
                AppConstants.progressSteps,
                AppConstants.totalSteps
            ))


        return mCombinedChartLiveData

    }

}
