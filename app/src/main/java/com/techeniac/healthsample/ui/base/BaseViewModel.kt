package com.techeniac.healthsample.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techeniac.healthsample.data.DataManager
import com.techeniac.healthsample.data.model.ChartModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by Robinson on 18 Nov 2021
 */
open class BaseViewModel<N>(
    val dataManager: DataManager
): ViewModel() {


    private var mNavigator: WeakReference<N>? = null
    val loader: MutableLiveData<Boolean> = MutableLiveData()
   // val error: MutableLiveData<ErrorData?> = MutableLiveData()

    private val mCombinedChartLiveData = MutableLiveData<ChartModel>()


    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun displayLoader(isLoading: Boolean) {
        loader.value = isLoading
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    fun getNavigator(): N? {
        return mNavigator?.get()
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }
}