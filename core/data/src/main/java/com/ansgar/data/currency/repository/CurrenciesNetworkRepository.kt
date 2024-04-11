package com.ansgar.data.currency.repository

import com.ansgar.base.CurrencyExResult
import com.ansgar.model.CurrencyModel

interface CurrenciesNetworkRepository {

    suspend fun getLatest(base: String): CurrencyExResult<CurrencyModel>
}