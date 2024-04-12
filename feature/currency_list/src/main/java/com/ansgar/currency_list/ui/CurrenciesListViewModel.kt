package com.ansgar.currency_list.ui

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.ansgar.base.BaseViewModel
import com.ansgar.base.onReceive
import com.ansgar.currency_list.ui.mapper.toDomainModel
import com.ansgar.currency_list.ui.mapper.toUiModel
import com.ansgar.currency_list.ui.model.CountryUiModel
import com.ansgar.design.location.getCountryFlag
import com.ansgar.domain.DeleteCurrencyUseCase
import com.ansgar.domain.GetCurrenciesLatestUseCase
import com.ansgar.domain.GetSavedCurrenciesUseCase
import com.ansgar.domain.SaveCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val DEFAULT_RATE = 1.0

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val getCurrencyLatestUseCase: GetCurrenciesLatestUseCase,
    private val getSavedCurrenciesUseCase: GetSavedCurrenciesUseCase,
    private val saveCurrenciesUseCase: SaveCurrencyUseCase,
) : BaseViewModel<CurrenciesListEvents, CurrenciesListUiState>(
    CurrenciesListUiState::class.java
) {

    private var currencyForDialog: CountryUiModel? = null

    override fun onCreate() {
        super.onCreate()
        getSavedCurrencies()
        getLatest()
    }

    fun onChangeSelectedValue(rateValue: String) {
        if (rateValue.isDigitsOnly()) {
            _uiState.value = _uiState.value.copy(rateValue = rateValue)
        }
    }

    fun onCalculate() {
        _uiState.value = _uiState.value.copy(
            currencies = _uiState.value.currencies.map { uiModel ->
                val rate = _uiState.value.rateValue

                uiModel.copy(
                    uiRate = uiModel.originalRate * rate.ifEmpty { DEFAULT_RATE.toString() }
                        .toDouble()
                )
            }
                .toCollection(ArrayList())
        )
    }

    fun showCurrencyDialog(show: Boolean, countryUiModel: CountryUiModel? = null) {
        currencyForDialog = countryUiModel

        _uiState.value = _uiState.value.copy(
            showCurrencyOptionDialog = show
        )
    }

    fun onOptionClick(position: Int) {
        when (CurrencyMenuOption.entries[position]) {
            CurrencyMenuOption.SELECT -> currencyForDialog?.let { onSelectCurrency(it) }
            CurrencyMenuOption.DELETE -> {
                val allCurrencies = _uiState.value.currencies.mapTo(ArrayList()) {
                    if (it.largeCode == currencyForDialog?.largeCode) {
                        it.copy(isSaved = !it.isSaved)
                    } else {
                        it
                    }
                }

                deleteFromDatabase()

                _uiState.value = _uiState.value.copy(
                    currencies = allCurrencies,
                    showCurrencyOptionDialog = false,
                )
            }
        }
    }

    private fun onSelectCurrency(currency: CountryUiModel) {
        val selectedCurrency = _uiState.value.selectedCurrency

        if (currency.largeCode == selectedCurrency.largeCode) {
            return
        }

        val currencies = _uiState.value.currencies
            .apply { add(selectedCurrency) }
            .mapTo(ArrayList()) {
                if (it.largeCode == currency.largeCode && !it.isSaved) {
                    it.copy(isSaved = true)
                } else {
                    it
                }
            }

        _uiState.value = _uiState.value.copy(
            selectedCurrency = currency,
            currencies = currencies,
            rateValue = "",
            showCurrencyOptionDialog = false,
        )

        saveCurrency(selectedCurrency)
        getLatest()
    }

    private fun updateCurrencies(currencies: ArrayList<CountryUiModel>) {
        val updatedCurrencies =
            currencies.mapTo(ArrayList()) { model ->
                val currency = _uiState.value.currencies.firstOrNull {
                    it.largeCode == model.largeCode
                }

                if (currency != null) {
                    model.copy(
                        icon = currency.icon,
                        largeCode = currency.largeCode,
                        smallCode = currency.smallCode,
                        name = currency.name,
                        isSaved = currency.isSaved
                    )
                } else {
                    model
                }
            }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currencies = updatedCurrencies
        )
    }

    private fun getLatest() {
        viewModelScope.launch {
            getCurrencyLatestUseCase(uiState.value.selectedCurrency.largeCode).onReceive(
                onSuccess = { response ->
                    val currencies: ArrayList<CountryUiModel> =
                        response.rates
                            .map { rate ->
                                rate.key.getCountryFlag()?.toUiModel(rate.value)
                            }
                            .filterNotNull()
                            .filter {
                                it.largeCode != _uiState.value.selectedCurrency.largeCode
                            }
                            .sortedBy { it.largeCode }
                            .toCollection(ArrayList())

                    updateCurrencies(currencies)
                },
                onFailure = { Timber.e(it.error) }
            )
        }
    }

    private fun saveCurrency(prevCurrency: CountryUiModel) {
        viewModelScope.launch {
            saveCurrenciesUseCase(prevCurrency.toDomainModel())
        }
    }

    private fun getSavedCurrencies() {
        viewModelScope.launch {
            val savedCurrencies = getSavedCurrenciesUseCase().mapTo(ArrayList()) { model ->
                model.largeCode
                    .getCountryFlag()
                    ?.toUiModel(DEFAULT_RATE)
                    ?.copy(isSaved = true)
            }
                .filterNotNull()

            _uiState.value = _uiState.value.copy(
                currencies = _uiState.value.currencies.apply {
                    addAll(savedCurrencies)
                }
            )
        }
    }

    private fun deleteFromDatabase() {
        viewModelScope.launch {
            currencyForDialog?.let {
                deleteCurrencyUseCase(it.toDomainModel())
            }
        }
    }
}
