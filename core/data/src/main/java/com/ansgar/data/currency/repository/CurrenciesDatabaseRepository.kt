package com.ansgar.data.currency.repository

import com.ansgar.model.CountryModel

interface CurrenciesDatabaseRepository {

    suspend fun getCurrencies(): List<CountryModel>

    suspend fun deleteCurrency(countryModel: CountryModel)

    suspend fun addOrUpdateCurrency(countryModel: CountryModel)
}