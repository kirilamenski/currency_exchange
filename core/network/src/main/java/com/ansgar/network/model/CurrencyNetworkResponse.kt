package com.ansgar.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrencyNetworkResponse(
    @SerializedName("meta") val meta: MetaCurrency?,
    @SerializedName("response") val response: CurrencyResponse?,
    @SerializedName("date") val date: String?,
    @SerializedName("base") val base: String?,
    @SerializedName("rates") val rates: Map<String, Double>?,
) : Serializable

data class MetaCurrency(
    @SerializedName("code") val code: Int?,
    @SerializedName("disclaimer") val disclaimer: String?,
) : Serializable

data class CurrencyResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("base") val base: String?,
    @SerializedName("rates") val rates: Map<String, Double>?,
) : Serializable