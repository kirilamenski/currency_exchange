package com.ansgar.design.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private val colorLightPrimary = Color(0xFFC73102)
private val colorLightTextPrimary = Color(0xFF000000)
private val colorLightTextSecondary = Color(0xFF6C727A)
private val colorLightBackground = Color(0xFFFFFFFF)
private val colorLightSecondaryBackground = Color(0xFFFFFFFF)
private val colorLightError = Color(0xFFFF3263)
private val colorLightDivider = Color(0xFF202020)
private val colorLightEditText = Color(0xFFE6E6E6)

private val colorDarkPrimary = Color(0xFFCE5935)
private val colorDarkTextPrimary = Color(0xFFFFFFFF)
private val colorDarkTextSecondary = Color(0xFF6C727A)
private val colorDarkBackground = Color(0xFF000000)
private val colorDarkSecondaryBackground = Color(0xFF363636)
private val colorDarkError = Color(0xFFFF3263)
private val colorDarkDivider = Color(0xFF202020)
private val colorDarkEditText = Color(0xFFA0A0A0)

class CurrenciesExchangeColors(
    background: Color,
    backgroundSecondary: Color,
    primary: Color,
    textPrimary: Color,
    textSecondary: Color,
    divider: Color,
    editText: Color,
    error: Color,
) {

    var background by mutableStateOf(background)
        private set
    var backgroundSecondary by mutableStateOf(backgroundSecondary)
        private set
    var primary by mutableStateOf(primary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var divider by mutableStateOf(divider)
        private set
    var editText by mutableStateOf(editText)
        private set
    var error by mutableStateOf(error)
        private set
}

fun lightColors(
    background: Color = colorLightBackground,
    backgroundSecondary: Color = colorLightSecondaryBackground,
    error: Color = colorLightError,
    primary: Color = colorLightPrimary,
    textPrimary: Color = colorLightTextPrimary,
    textSecondary: Color = colorLightTextSecondary,
    divider: Color = colorLightDivider,
    editText: Color = colorLightEditText,
): CurrenciesExchangeColors = CurrenciesExchangeColors(
    background = background,
    backgroundSecondary = backgroundSecondary,
    error = error,
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    divider = divider,
    editText = editText,
)

fun darkColors(
    background: Color = colorDarkBackground,
    backgroundSecondary: Color = colorDarkSecondaryBackground,
    error: Color = colorDarkError,
    primary: Color = colorDarkPrimary,
    textPrimary: Color = colorDarkTextPrimary,
    textSecondary: Color = colorDarkTextSecondary,
    divider: Color = colorDarkDivider,
    editText: Color = colorDarkEditText,
): CurrenciesExchangeColors = CurrenciesExchangeColors(
    background = background,
    backgroundSecondary = backgroundSecondary,
    error = error,
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = textSecondary,
    divider = divider,
    editText = editText,
)
