package com.ansgar.data.currency.repository

import com.ansgar.base.CurrencyExResult
import com.ansgar.data.currency.mapper.toModel
import com.ansgar.model.CurrencyModel
import com.ansgar.network.api.CurrencyApi
import com.ansgar.network.safeApiCall
import javax.inject.Inject

class CurrenciesNetworkRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApi
) : CurrenciesNetworkRepository {

    override suspend fun getLatest(base: String): CurrencyExResult<CurrencyModel> =
        safeApiCall {
            currencyApi.getLatest(base).toModel()
        }
}