package com.ansgar.domain

import com.ansgar.base.constants.SELECTED_CURRENCY
import com.ansgar.data.currency.repository.CurrenciesDatabaseRepository
import com.ansgar.data.shared_pref.CurrencyExSharedPreferencesImpl
import com.ansgar.model.CountryModel
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(
    private val currenciesDatabaseRepository: CurrenciesDatabaseRepository,
    private val sharedPreferences: CurrencyExSharedPreferencesImpl?,
) {

    suspend operator fun invoke(selectedCurrency: CountryModel, prevCurrency: CountryModel) {
        sharedPreferences?.put(SELECTED_CURRENCY, prevCurrency.largeCode)
        currenciesDatabaseRepository.addOrUpdateCurrency(selectedCurrency)
    }
}