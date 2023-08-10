package com.krunal.mychat.ui.splash

import androidx.activity.viewModels
import com.krunal.mychat.R
import com.krunal.mychat.ui.base.BaseAppCompatActivity
import com.krunal.mychat.databinding.ActivitySplashBinding
import com.krunal.mychat.ui.activity.AuthActivity
import com.krunal.mychat.ui.sample.SampleActivity
import com.krunal.mychat.utils.extension.launchActivity
import com.krunal.mychat.utils.extension.observeEvent
import com.krunal.mychat.ui.splash.SplashViewModel.Destination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseAppCompatActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun setupViewModel() {
        super.setupViewModel()

        viewModel.goToScreen.observeEvent(this) { destination ->
            when (destination) {
                Destination.Home -> openHomeScreen()
                Destination.Login -> openLoginScreen()
                Destination.Sample -> openSampleScreen()
            }
        }
    }

    private fun openLoginScreen() {
        launchActivity<AuthActivity>()
    }

    private fun openHomeScreen() {
        // TODO : Open Home screen
    }

    private fun openSampleScreen() {
        launchActivity<SampleActivity>()
    }
}
