package com.ansgar.currency_list.ui.model

data class CountryUiModel(
    val name: String = "",
    val smallCode: String = "",
    val largeCode: String = "",
    val icon: Int = -1,
    val originalRate: Double = -1.0,
    val uiRate: Double = -1.0,
    val isSaved: Boolean = false,
)