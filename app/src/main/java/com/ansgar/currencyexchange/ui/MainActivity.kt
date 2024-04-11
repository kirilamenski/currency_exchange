package com.ansgar.currencyexchange.ui

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ansgar.base.activity.BaseActivity
import com.ansgar.currencyexchange.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private const val DURATION = 500L
private const val SCALE_FROM = 0.4f
private const val SCALE_TO = 0.0f

@AndroidEntryPoint
class MainActivity :
    BaseActivity<MainActivityEvents, MainActivityUiState, ActivityMainBinding, MainActivityViewModel>(
        MainActivityViewModel::class.java
    ) {

    override fun setUpSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }

            setOnExitAnimationListener { screen ->
                ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    SCALE_FROM,
                    SCALE_TO
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = DURATION
                    doOnEnd { screen.remove() }
                }.start()

                ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    SCALE_FROM,
                    SCALE_TO
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = DURATION
                    doOnEnd { screen.remove() }
                }.start()
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun observeEvent(event: MainActivityEvents) {
    }
}
