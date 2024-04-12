package com.ansgar.currency_list.ui

import androidx.annotation.StringRes
import com.ansgar.currency_list.ui.model.CountryUiModel
import com.ansgar.design.R

data class CurrenciesListUiState(
    val isLoading: Boolean = false,
    val showCurrencyOptionDialog: Boolean = false,
    val currencies: ArrayList<CountryUiModel> = arrayListOf(),
    val selectedCurrency: CountryUiModel = CountryUiModel(),
    val rateValue: String = ""
)

enum class CurrencyMenuOption(@StringRes val optionTextRes: Int) {
    SELECT(R.string.select),
    DELETE(R.string.delete);
}
