package com.ansgar.network

import com.ansgar.base.CurrencyExResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    call: suspend () -> T
): CurrencyExResult<T> = withContext(dispatcher) {
    try {
        CurrencyExResult.Success(call.invoke())
    } catch (throwable: Throwable) {
        Timber.e(throwable)

        when (throwable) {
            is HttpException -> convertNetworkError(throwable)
            else -> CurrencyExResult.Error(error = throwable)
        }
    }
}

private fun convertNetworkError(error: Throwable): CurrencyExResult.Error = try {
    when (error) {
        is HttpException -> {
            val errorBody = error.response()?.errorBody()?.string()
        }
    }
    CurrencyExResult.Error(error = error)
} catch (e: Exception) {
    CurrencyExResult.Error(error = error)
}