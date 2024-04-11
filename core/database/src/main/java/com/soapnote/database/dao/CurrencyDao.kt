package com.soapnote.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.soapnote.database.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_entity")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Upsert
    suspend fun addOrUpdate(currencyEntity: CurrencyEntity)

    @Delete
    suspend fun deleteCurrency(currencyEntity: CurrencyEntity)
}