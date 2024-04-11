package com.ansgar.domain

import com.ansgar.data.currency.repository.CurrenciesDatabaseRepository
import com.ansgar.model.CountryModel
import javax.inject.Inject

class GetSavedCurrenciesUseCase @Inject constructor(
    private val currenciesDatabaseRepository: CurrenciesDatabaseRepository
) {

    suspend operator fun invoke(): List<CountryModel> =
        currenciesDatabaseRepository.getCurrencies()
}