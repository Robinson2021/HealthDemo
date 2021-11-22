package com.techeniac.healthsample.ui.launch

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.techeniac.healthsample.BR
import com.techeniac.healthsample.R
import com.techeniac.healthsample.databinding.ActivityLauncherBinding
import com.techeniac.healthsample.ui.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class LauncherActivity : BaseActivity<ActivityLauncherBinding, LauncherViewModel>(),
    LauncherNavigator, HasAndroidInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var launchViewModel: LauncherViewModel

    override fun getLayoutId(): Int {
        return R.layout.activity_launcher
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LauncherViewModel {
        launchViewModel =
            ViewModelProvider(this, viewModelFactory).get(LauncherViewModel::class.java)
        return launchViewModel
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchViewModel.setNavigator(this)
    }
}
