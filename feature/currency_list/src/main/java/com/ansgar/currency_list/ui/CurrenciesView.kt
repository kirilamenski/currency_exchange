package com.ansgar.currency_list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ansgar.currency_list.ui.model.CountryUiModel
import com.ansgar.design.R
import com.ansgar.design.components.CurrencyExOptionDialog
import com.ansgar.design.theme.CurrenciesExchangeAppTheme
import com.ansgar.design.theme.CurrenciesExchangeTheme

private const val INPUT_FIELD_FRACTION = 0.7f
private const val SELECTED_CURRENCY_NAME_FRACTION = 0.3f
private const val SHIMMER_FRACTION = 0.25f
private const val CURRENCIES_SHIMMERS_COUNT = 10

@Composable
internal fun CurrenciesView(
    viewModel: CurrenciesListViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxWidth()) {
        CurrenciesContentView(
            modifier = Modifier.padding(it),
            uiState = uiState,
            onDone = viewModel::onCalculate,
            onValueChange = viewModel::onChangeSelectedValue,
            onLongClick = { model ->
                viewModel.showCurrencyDialog(show = true, countryUiModel = model)
            }
        )

        if (uiState.showCurrencyOptionDialog) {
            CurrencyExOptionDialog(
                options = CurrencyMenuOption.entries
                    .mapTo(ArrayList()) { option -> stringResource(id = option.optionTextRes) }
                    .toTypedArray(),
                onDismiss = { viewModel.showCurrencyDialog(show = false) },
                onOptionClick = { position -> viewModel.onOptionClick(position) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CurrenciesContentView(
    uiState: CurrenciesListUiState,
    onLongClick: (CountryUiModel) -> Unit,
    onDone: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CurrenciesExchangeTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CurrenciesExchangeTheme.dimensions.size2xs)
    ) {
        CurrenciesViewSelectedCurrency(
            onDone = onDone,
            value = uiState.rateValue,
            selectedCurrency = uiState.selectedCurrency,
            onValueChange = onValueChange
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (uiState.currencies.isEmpty()) {
                currenciesShimmers()
            } else {
                uiState.currencies
                    .sortedBy { !it.isSaved }
                    .groupBy { !it.isSaved }
                    .forEach { (label, groupedCurrencies) ->
                        stickyHeader {
                            LabelListItem(
                                label = if (!label) {
                                    stringResource(id = R.string.recent_currencies)
                                } else {
                                    stringResource(id = R.string.all_currencies)
                                }
                            )
                        }

                        rateItems(
                            currencies = groupedCurrencies,
                            onLongClick = onLongClick,
                        )
                    }
            }
        }
    }
}

@Composable
private fun CurrenciesViewSelectedCurrency(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    selectedCurrency: CountryUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(CurrenciesExchangeTheme.dimensions.sizeXs),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(SELECTED_CURRENCY_NAME_FRACTION),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CurrenciesExchangeTheme.dimensions.sizeXs),
        ) {
            Image(
                modifier = Modifier.size(CurrenciesExchangeTheme.dimensions.size2xl),
                painter = painterResource(id = selectedCurrency.icon),
                contentDescription = selectedCurrency.name,
            )

            Text(
                text = selectedCurrency.largeCode,
                color = CurrenciesExchangeTheme.colors.textPrimary,
                style = CurrenciesExchangeTheme.typography.titleLarge,
            )
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth(INPUT_FIELD_FRACTION)
                .border(
                    border = CurrenciesExchangeTheme.borders.defaultBorder,
                    shape = CurrenciesExchangeTheme.shapes.shapeSm
                ),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Decimal
            ),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            textStyle = CurrenciesExchangeTheme.typography
                .textMedium
                .copy(
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    color = CurrenciesExchangeTheme.colors.textPrimary
                ),
            shape = CurrenciesExchangeTheme.shapes.shapeSm,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = CurrenciesExchangeTheme.colors.background,
                unfocusedContainerColor = CurrenciesExchangeTheme.colors.background,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.input_rate_hint),
                    style = CurrenciesExchangeTheme.typography.textMedium,
                    color = CurrenciesExchangeTheme.colors.textPrimary,
                    textAlign = TextAlign.End,
                )
            }
        )
    }
}

private fun LazyListScope.rateItems(
    currencies: List<CountryUiModel>,
    onLongClick: (CountryUiModel) -> Unit,
) = items(
    items = currencies,
    key = { it.largeCode }
) { item ->
    CurrenciesListItem(
        currency = item,
        onLongClick = onLongClick,
    )

    HorizontalDivider(color = CurrenciesExchangeTheme.colors.divider)
}

private fun LazyListScope.currenciesShimmers() = items(count = CURRENCIES_SHIMMERS_COUNT) {
    CurrencyShimmerView()

    HorizontalDivider()
}

@Composable
private fun LabelListItem(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .background(CurrenciesExchangeTheme.colors.background)
            .fillMaxWidth()
            .padding(CurrenciesExchangeTheme.dimensions.sizeXs),
        text = label,
        style = CurrenciesExchangeTheme.typography.titleLarge,
        color = CurrenciesExchangeTheme.colors.textSecondary,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CurrenciesListItem(
    currency: CountryUiModel,
    onLongClick: (CountryUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .combinedClickable(
                onClick = { /*no-op*/ },
                onLongClick = { onLongClick(currency) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CurrenciesExchangeTheme.dimensions.sizeXs),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = CurrenciesExchangeTheme.dimensions.sizeXs
                ),
            ) {
                if (currency.icon != -1) {
                    Image(
                        modifier = Modifier.size(CurrenciesExchangeTheme.dimensions.sizeXl),
                        painter = painterResource(id = currency.icon),
                        contentDescription = currency.name,
                    )
                }

                Text(
                    text = currency.largeCode,
                    color = CurrenciesExchangeTheme.colors.textPrimary,
                    style = CurrenciesExchangeTheme.typography.textMedium,
                )
            }

            Text(
                text = stringResource(id = R.string.rate_format, currency.uiRate),
                color = CurrenciesExchangeTheme.colors.textPrimary,
                style = CurrenciesExchangeTheme.typography.textMedium,
            )
        }
    }
}

@Composable
private fun CurrencyShimmerView(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(CurrenciesExchangeTheme.colors.background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(CurrenciesExchangeTheme.dimensions.sizeXs),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = CurrenciesExchangeTheme.dimensions.sizeXs
                ),
            ) {
                Spacer(
                    modifier = Modifier
                        .clip(CurrenciesExchangeTheme.shapes.shape2xs)
                        .size(CurrenciesExchangeTheme.dimensions.sizeXl)
                        .background(CurrenciesExchangeTheme.colors.shimmer),
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(SHIMMER_FRACTION)
                        .clip(CurrenciesExchangeTheme.shapes.shape2xs)
                        .height(CurrenciesExchangeTheme.dimensions.sizeXl)
                        .background(CurrenciesExchangeTheme.colors.shimmer)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(SHIMMER_FRACTION)
                    .clip(CurrenciesExchangeTheme.shapes.shape2xs)
                    .height(CurrenciesExchangeTheme.dimensions.sizeXl)
                    .background(CurrenciesExchangeTheme.colors.shimmer)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun CurrenciesViewLoadingPreview() {
    CurrenciesExchangeAppTheme {
        CurrenciesContentView(
            uiState = CurrenciesListUiState(),
            onDone = {},
            onValueChange = {},
            onLongClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun CurrenciesViewDarkPreview() {
    CurrenciesExchangeAppTheme {
        CurrenciesContentView(
            uiState = CurrenciesListUiState(
                rateValue = "123",
                selectedCurrency = CountryUiModel(
                    name = "Belarus",
                    smallCode = "",
                    largeCode = "BYN",
                    uiRate = 1.0,
                    icon = R.drawable.by,
                ),
                currencies = arrayListOf(
                    CountryUiModel(
                        name = "Belarus",
                        smallCode = "",
                        largeCode = "BYN",
                        uiRate = 1.0,
                        icon = R.drawable.by,
                        isSaved = true
                    ),
                    CountryUiModel(
                        name = "European Union",
                        smallCode = "",
                        largeCode = "EUR",
                        uiRate = 1.0,
                        icon = R.drawable.euro,
                        isSaved = true
                    ),
                    CountryUiModel(
                        name = "US",
                        smallCode = "US",
                        largeCode = "USD",
                        uiRate = 1.0,
                        icon = R.drawable.us,
                    ),
                )
            ),
            onDone = {},
            onValueChange = {},
            onLongClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun CurrencyShimmerViewPreview() {
    CurrencyShimmerView()
}
