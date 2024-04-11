package com.soapnote.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soapnote.database.dao.CurrencyDao
import com.soapnote.database.entity.CurrencyEntity

internal const val CURRENCIES_EX_DATABASE_NAME = "currencies_db_.sql"
private const val DB_VERSION = 1

@Database(
    entities = [
        CurrencyEntity::class,
    ],
    version = DB_VERSION
)
abstract class CurrenciesExRoom : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}