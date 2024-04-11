package com.ansgar.base.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.ansgar.base.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseActivity<E, US, B : ViewBinding, VM : BaseViewModel<E, US>>(
    private val clazz: Class<VM>
) : FragmentActivity() {

    protected lateinit var viewModel: VM
    private lateinit var binding: B

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSplashScreen()

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[clazz]
        viewModel.onCreate()

        binding = getViewBinding()
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventFlow.collect {
                    observeEvent(it)
                }
            }
        }
    }

    @CallSuper
    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    abstract fun observeEvent(event: E)

    abstract fun getViewBinding(): B

    open fun setUpSplashScreen() {}
}