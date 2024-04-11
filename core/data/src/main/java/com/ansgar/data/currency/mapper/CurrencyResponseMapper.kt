package com.ansgar.data.currency.mapper

import com.ansgar.model.CountryModel
import com.ansgar.model.CurrencyModel
import com.ansgar.model.CurrencyRateModel
import com.ansgar.model.MetaModel
import com.ansgar.network.model.CurrencyNetworkResponse
import com.soapnote.database.entity.CurrencyEntity

internal fun CurrencyNetworkResponse.toModel() = CurrencyModel(
    meta = MetaModel(
        code = meta?.code ?: -1,
        disclaimer = meta?.disclaimer.orEmpty(),
    ),
    response = CurrencyRateModel(
        base = response?.date.orEmpty(),
        date = response?.date.orEmpty(),
        rates = response?.rates ?: emptyMap()
    ),
    date = date.orEmpty(),
    base = base.orEmpty(),
    rates = rates ?: emptyMap()
)

internal fun CurrencyEntity.toModel() = CountryModel(
    largeCode = currencyName,
)

internal fun CountryModel.toEntity() = CurrencyEntity(
    currencyName = largeCode,
)