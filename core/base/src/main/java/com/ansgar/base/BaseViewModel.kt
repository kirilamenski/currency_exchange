package com.ansgar.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<E, US>(private val usClazz: Class<US>) : ViewModel() {

    protected val _uiState = MutableStateFlow(usClazz.getDeclaredConstructor().newInstance())
    val uiState: StateFlow<US> = _uiState

    private val eventChannel = Channel<E>()
    val eventFlow = eventChannel.receiveAsFlow()

    protected var page: Int = 1
        private set

    @CallSuper
    open fun onCreate() {
    }

    @CallSuper
    open fun onDestroy() {
    }

    protected fun resetPage() {
        page = 1
    }

    protected fun incrementPage() {
        page++
    }

    fun sendEvent(event: E) {
        viewModelScope.launch(Dispatchers.IO) {
            eventChannel.send(event)
        }
    }
}