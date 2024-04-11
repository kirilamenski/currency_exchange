package com.ansgar.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ansgar.design.R
import com.ansgar.design.theme.CurrenciesExchangeAppTheme
import com.ansgar.design.theme.CurrenciesExchangeTheme

@Composable
fun CurrencyExOptionDialog(
    options: Array<String>,
    onDismiss: () -> Unit,
    onOptionClick: (Int) -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    ),
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = properties,
    ) {
        Card(
            modifier = Modifier,
            shape = CurrenciesExchangeTheme.shapes.shapeSm,
            colors = CardDefaults.cardColors(
                containerColor = CurrenciesExchangeTheme.colors.backgroundSecondary,
            ),
        ) {
            Column(
                modifier = Modifier.padding(CurrenciesExchangeTheme.dimensions.sizeSm),
                horizontalAlignment = Alignment.End,
            ) {
                options.forEachIndexed { index, option ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = CurrenciesExchangeTheme.shapes.shape2xs,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                        onClick = { onOptionClick(index) }
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = CurrenciesExchangeTheme.dimensions.sizeSm,
                                horizontal = CurrenciesExchangeTheme.dimensions.sizeXs,
                            ),
                            text = option,
                            style = CurrenciesExchangeTheme.typography.textMedium,
                            color = CurrenciesExchangeTheme.colors.textPrimary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(CurrenciesExchangeTheme.dimensions.size2xl))

                Button(
                    onClick = onDismiss,
                    shape = CurrenciesExchangeTheme.shapes.shape2xs,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        color = CurrenciesExchangeTheme.colors.error,
                        style = CurrenciesExchangeTheme.typography.textMedium,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CurrencyExOptionDialogPreview() {
    CurrenciesExchangeAppTheme {
        CurrencyExOptionDialog(
            onDismiss = {},
            onOptionClick = {},
            options = arrayOf(
                "Select",
                "Delete",
            )
        )
    }
}
