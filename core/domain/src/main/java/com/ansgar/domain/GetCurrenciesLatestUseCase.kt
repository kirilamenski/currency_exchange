package com.ansgar.domain

import com.ansgar.base.CurrencyExResult
import com.ansgar.data.currency.repository.CurrenciesNetworkRepository
import com.ansgar.model.CurrencyModel
import javax.inject.Inject

class GetCurrenciesLatestUseCase @Inject constructor(
    private val currenciesRepository: CurrenciesNetworkRepository
) {

    suspend operator fun invoke(base: String) : CurrencyExResult<CurrencyModel> =
        currenciesRepository.getLatest(base)
}