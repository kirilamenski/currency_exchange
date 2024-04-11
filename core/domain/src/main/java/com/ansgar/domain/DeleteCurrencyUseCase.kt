package com.ansgar.domain

import com.ansgar.data.currency.repository.CurrenciesDatabaseRepository
import com.ansgar.model.CountryModel
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: CurrenciesDatabaseRepository
) {

    suspend operator fun invoke(countryModel: CountryModel) {
        repository.deleteCurrency(countryModel)
    }
}