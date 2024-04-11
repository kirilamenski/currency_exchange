package com.ansgar.network.api

import com.ansgar.network.model.CurrencyNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest")
    suspend fun getLatest(@Query("base") base: String): CurrencyNetworkResponse
}