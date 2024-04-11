package com.ansgar.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object CurrenciesExchangeTheme {

    val colors: CurrenciesExchangeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
    val typography: CurrenciesExchangeTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
    val dimensions: CurrenciesExchangeDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
    val shapes: CurrenciesExchangeShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
    val borders: CurrenciesExchangeBorders
        @Composable
        @ReadOnlyComposable
        get() = LocalBorders.current
    val elevations: CurrenciesExchangeElevations
        @Composable
        @ReadOnlyComposable
        get() = LocalElevations.current
}

@Composable
fun CurrenciesExchangeAppTheme(
    typography: CurrenciesExchangeTypography = CurrenciesExchangeTheme.typography,
    dimensions: CurrenciesExchangeDimensions = CurrenciesExchangeTheme.dimensions,
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme()) {
        darkColors()
    } else {
        lightColors()
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalDimensions provides dimensions,
        LocalTypography provides typography
    ) {
        content()
    }
}

private val LocalColors = staticCompositionLocalOf { lightColors() }
private val LocalTypography = staticCompositionLocalOf { CurrenciesExchangeTypography() }
private val LocalDimensions = staticCompositionLocalOf { CurrenciesExchangeDimensions() }
private val LocalShapes = staticCompositionLocalOf { CurrenciesExchangeShapes() }
private val LocalBorders = staticCompositionLocalOf { CurrenciesExchangeBorders() }
private val LocalElevations = staticCompositionLocalOf { CurrenciesExchangeElevations() }