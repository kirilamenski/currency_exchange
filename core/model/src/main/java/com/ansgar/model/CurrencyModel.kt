package com.ansgar.model

data class CurrencyModel(
    val meta: MetaModel = MetaModel(),
    val response: CurrencyRateModel = CurrencyRateModel(),
    val date: String = "",
    val base: String = "",
    val rates: Map<String, Double> = emptyMap(),
)

data class MetaModel(
    val code: Int = -1,
    val disclaimer: String = "",
)

data class CurrencyRateModel(
    val date: String = "",
    val base: String = "",
    val rates: Map<String, Double> = emptyMap(),
)
