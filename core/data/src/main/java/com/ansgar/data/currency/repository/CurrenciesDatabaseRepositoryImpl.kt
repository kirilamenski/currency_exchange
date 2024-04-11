package com.ansgar.data.currency.repository

import com.ansgar.data.currency.mapper.toEntity
import com.ansgar.data.currency.mapper.toModel
import com.ansgar.model.CountryModel
import com.soapnote.database.dao.CurrencyDao
import javax.inject.Inject

class CurrenciesDatabaseRepositoryImpl @Inject constructor(
    private val currenciesDao: CurrencyDao
) : CurrenciesDatabaseRepository {

    override suspend fun getCurrencies(): List<CountryModel> =
        currenciesDao.getAllCurrencies().map { it.toModel() }

    override suspend fun deleteCurrency(countryModel: CountryModel) {
        currenciesDao.deleteCurrency(countryModel.toEntity())
    }

    override suspend fun addOrUpdateCurrency(countryModel: CountryModel) {
        currenciesDao.addOrUpdate(countryModel.toEntity())
    }
}