package com.ansgar.model

data class CountryModel(
    val name: String = "",
    val smallCode: String = "",
    val largeCode: String = "",
    val icon: Int = -1,
    val originalRate: Double = -1.0,
    val uiRate: Double = -1.0,
)