package com.soapnote.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_entity")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_name")
    val currencyName: String,
)