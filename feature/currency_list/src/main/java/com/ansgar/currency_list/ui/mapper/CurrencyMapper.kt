package com.ansgar.currency_list.ui.mapper

import com.ansgar.currency_list.ui.model.CountryUiModel
import com.ansgar.design.location.CountryFlag
import com.ansgar.model.CountryModel

internal fun CountryFlag.toUiModel(rate: Double) = CountryUiModel(
    name = countryName,
    smallCode = smallCode,
    largeCode = largeCode,
    icon = icon,
    uiRate = rate,
    originalRate = rate
)

internal fun CountryUiModel.toDomainModel() = CountryModel(
    name = name,
    smallCode = smallCode,
    largeCode = largeCode,
    icon = icon,
    uiRate = uiRate,
    originalRate = originalRate
)