package com.ansgar.currencyexchange.ui

import androidx.lifecycle.viewModelScope
import com.ansgar.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLASH_SCREEN_DELAY = 3000L

@HiltViewModel
class MainActivityViewModel @Inject constructor() :
    BaseViewModel<MainActivityEvents, MainActivityUiState>(MainActivityUiState::class.java) {

        private val _isReady = MutableStateFlow(false)
        val isReady = _isReady.asStateFlow()

        init {
            viewModelScope.launch {
                delay(SPLASH_SCREEN_DELAY)
                _isReady.value = true
            }
        }
}
