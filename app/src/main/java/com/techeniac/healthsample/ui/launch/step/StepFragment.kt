package com.techeniac.healthsample.ui.launch.step

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.techeniac.healthsample.BR
import com.techeniac.healthsample.R
import com.techeniac.healthsample.databinding.FragmentStepBinding
import com.techeniac.healthsample.databinding.ItemLegendBinding
import com.techeniac.healthsample.ui.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt


/**
 * Created by Robinson on 18 Nov 2021
 */
class StepFragment : BaseFragment<FragmentStepBinding, StepViewModel>(), StepNavigator {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mStepViewModel: StepViewModel

    /**
     *  creating a string array for displaying days.
     */


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_step
    }

    override fun getViewModel(): StepViewModel {
        mStepViewModel = ViewModelProvider(this, viewModelFactory).get(StepViewModel::class.java)
        return mStepViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStepViewModel.setNavigator(this)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getViewDataBinding().ivAdd.setOnClickListener {
            /**
             *  Add 100 Steps
             */
            addSteps(100)
        }
    }


    /**
     * Gets Chart data and displays on screen
     * */
    private fun getChartData() {
        // 1 Day 2 Week 3 Months
        // default Weekly
        mStepViewModel.getChartData(
            requireContext(), 2
        ).observe(this, { item ->
            // Update the UI.

            /**
             *  Display LineData, BarData
             */
            displayLineBarChart(item.mLineData, item.mBarData, item.days)

            /**
             * Display Heart rate data
             */
            displayHeartLineChart(item.mHeartLineData)

            /**
             * * Pie data
             */
            displayPieChart(item.listPie, item.listColors)


            /**
             *  Animate Step Counter
             */
            animateStepCounter(item.steps)
        })
    }

    private fun addSteps(steps: Int) {
        getStepProgress(steps)
    }

    private fun getStepProgress(steps: Int) {
        mStepViewModel.getStepProgress(steps).observe(this, { item ->
            displayStepProgress(item)
        })
    }

    override fun onResume() {
        super.onResume()
        /**
         * Get all the charts dummy data
         */
        getChartData()

        /**
         *  Get steps data and display
         */
        getStepProgress(0)
    }

    private fun animateStepCounter(steps: Int) {
        val animator = ValueAnimator()
        animator.setObjectValues(0, steps)
        animator.addUpdateListener { animation ->
            getViewDataBinding().tvSteps.text = animation.animatedValue.toString()
        }
        animator.setEvaluator(TypeEvaluator<Int> { fraction, startValue, endValue ->
            (startValue + (endValue - startValue) * fraction).roundToInt()
        })
        animator.duration = 1000
        animator.start()
    }

    private fun displayStepProgress(totalSteps: Int) {
        getViewDataBinding().progressCircular.progress = 0   // Main Progress
        getViewDataBinding().progressCircular.max = totalSteps // Maximum Progress

        /**
         *  Animate progress circle
         */
        val animator = ValueAnimator()
        animator.setObjectValues(0, totalSteps)
        animator.addUpdateListener { animation ->
            val getValue = animation.animatedValue.toString()
            getViewDataBinding().tvProgressSteps.text = getValue
            val res: Int = getValue.toInt() / 2
            getViewDataBinding().progressCircular.progress = res
            Timber.d("stepProgress : getValue%s  ", getValue)
            Timber.d("stepProgress : res%s  ", res)
        }
        animator.setEvaluator(TypeEvaluator<Int> { fraction, startValue, endValue ->
            (startValue + (endValue - startValue) * fraction).roundToInt()
        })
        animator.duration = 1000
        animator.start()
    }


    private fun displayHeartLineChart(mHeartLineData: LineData) {
        getViewDataBinding().lineChart.description.isEnabled = false
        getViewDataBinding().lineChart.axisRight.isEnabled = false
        getViewDataBinding().lineChart.legend.isEnabled = false
        getViewDataBinding().lineChart.setDrawGridBackground(false)
        getViewDataBinding().lineChart.setPinchZoom(false)
        getViewDataBinding().lineChart.setScaleEnabled(false)
        getViewDataBinding().lineChart.isDoubleTapToZoomEnabled = false
        getViewDataBinding().lineChart.isScaleYEnabled = false
        getViewDataBinding().lineChart.isDragXEnabled = false
        getViewDataBinding().lineChart.isDragYEnabled = false

        val xAxis: XAxis = getViewDataBinding().lineChart.xAxis
        xAxis.isEnabled = false
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        val yAxisHeart: YAxis = getViewDataBinding().lineChart.axisLeft
        yAxisHeart.isEnabled = false
        yAxisHeart.axisMaximum = 220f
        //yAxisHeart.axisMinimum = -100f
        yAxisHeart.setDrawAxisLine(false)
        yAxisHeart.setDrawZeroLine(false)

        /**
         *  get dummy data
         */
        getViewDataBinding().lineChart.data = mHeartLineData

        //getViewDataBinding().lineChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // getViewDataBinding().lineChart.renderer.paintRender.setShadowLayer(3F, 5F, 3F, Color.GRAY);

        getViewDataBinding().lineChart.setViewPortOffsets(0F, 0F, 0F, 0F)
        getViewDataBinding().lineChart.animateX(2000)
    }


    private fun displayLineBarChart(mLineData: LineData, mBarData: BarData, days: Array<String>) {
        //  mCombinedChart.setDescription(Description())
        getViewDataBinding().combinedChart.setBackgroundColor(Color.WHITE)
        getViewDataBinding().combinedChart.setDrawGridBackground(false)
        getViewDataBinding().combinedChart.setDrawBarShadow(false)
        getViewDataBinding().combinedChart.legend.isEnabled = true
        getViewDataBinding().combinedChart.description.isEnabled = false

        //set space between legend and axis
        getViewDataBinding().combinedChart.setExtraOffsets(5f, 5f, 5f, 15f)

        // draw bars behind lines
        getViewDataBinding().combinedChart.drawOrder = arrayOf(
            DrawOrder.BAR, DrawOrder.LINE
        )
        getViewDataBinding().combinedChart.setTouchEnabled(false)
        // getViewDataBinding().combinedChart.isHorizontalScrollBarEnabled = true
        //  getViewDataBinding().combinedChart.setPinchZoom(false)

        val rightAxis: YAxis = getViewDataBinding().combinedChart.axisRight
        rightAxis.setDrawGridLines(false)

        val leftAxis: YAxis = getViewDataBinding().combinedChart.axisLeft
        leftAxis.setDrawGridLines(false)

        val xAxis: XAxis = getViewDataBinding().combinedChart.xAxis
        xAxis.position = XAxisPosition.BOTH_SIDED
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        val data = CombinedData()

        data.setData(mLineData)
        data.setData(mBarData)

        getViewDataBinding().combinedChart.data = data
        //getViewDataBinding().combinedChart.setDrawBarShadow(true)
        getViewDataBinding().combinedChart.animateY(2000)
        //getViewDataBinding().combinedChart.animateX(2000)
        getViewDataBinding().combinedChart.invalidate()

    }


    private fun displayPieChart(listPie: ArrayList<PieEntry>, listColors: ArrayList<Int>) {
        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors

        val pieData = PieData(pieDataSet)

        getViewDataBinding().pieChart.data = pieData
        getViewDataBinding().pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create pieChart hole in center
        getViewDataBinding().pieChart.holeRadius = 40f
        // pieChart.transparentCircleRadius = 61f
        getViewDataBinding().pieChart.isDrawHoleEnabled = true
        getViewDataBinding().pieChart.setHoleColor(Color.WHITE)

        // mPieChart.setTouchEnabled(false)

        //add text in center ,color (default disable)
        getViewDataBinding().pieChart.centerText = getBaseActivity().getString(R.string.steps)
        getViewDataBinding().pieChart.setCenterTextColor(Color.BLACK)
        getViewDataBinding().pieChart.setDrawCenterText(false)


        //add label ,color in pieChart (default disable)
        getViewDataBinding().pieChart.setDrawEntryLabels(false)
        getViewDataBinding().pieChart.setEntryLabelColor(Color.BLACK)

        //add label value in pieChart (default disable)
        pieData.setDrawValues(false)
        getViewDataBinding().pieChart.setUsePercentValues(false)


        //disable pieChart  description
        getViewDataBinding().pieChart.description.isEnabled = false


        //disable pieChart  legend
        getViewDataBinding().pieChart.legend.isEnabled = false

        /**
         *  Create Custom PieChart legend
         */
        setPieChartCustomLegend(getViewDataBinding().legendLlLayout, listColors, listPie)
    }

    private fun setPieChartCustomLegend(
        parentLayout: LinearLayout,
        listColors: ArrayList<Int>,
        listPie: ArrayList<PieEntry>
    ) {
        parentLayout.removeAllViews()
        for (i in listColors.indices) {
            val mBinding: ItemLegendBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_legend,
                parentLayout,
                false
            )
            mBinding.tvStepName.text = listPie[i].label
         //   mBinding.tvStepName.text = listPie[i].label +" : "+listPie[i].value.toInt()+" Steps"
            mBinding.ivDot.setColorFilter(listColors[i])

            parentLayout.addView(mBinding.root)
        }
    }

    override fun activity(): Activity {
        return requireActivity()
    }

    override fun context(): Context {
        return requireContext()
    }

}