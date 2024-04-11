package com.ansgar.base

sealed class CurrencyExResult<out T> {
    data class Success<out T>(val response: T) : CurrencyExResult<T>()
    data class Error(
        val code: Int? = null,
        val message: String = "",
        val error: Throwable
    ) : CurrencyExResult<Nothing>()
}

inline fun <T> CurrencyExResult<T>.onReceive(
    onSuccess: (T) -> Unit = {},
    onFailure: (CurrencyExResult.Error) -> Unit = {}
) {
    when (this) {
        is CurrencyExResult.Success -> onSuccess(this.response)
        is CurrencyExResult.Error -> onFailure(this)
    }
}