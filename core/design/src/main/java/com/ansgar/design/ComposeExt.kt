package com.ansgar.design

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.ansgar.design.theme.CurrenciesExchangeAppTheme

fun Fragment.createContentView(content: @Composable () -> Unit): View =
    ComposeView(requireContext()).setThemedContent(content)

fun ComposeView.setThemedContent(content: @Composable () -> Unit): View = apply {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent { CurrenciesExchangeAppTheme { content() } }
}