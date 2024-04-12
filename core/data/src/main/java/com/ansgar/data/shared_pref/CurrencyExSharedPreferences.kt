package com.ansgar.data.shared_pref

interface CurrencyExSharedPreferences {

    fun put(key: String, value: Any)

    fun get(key: String, defaultValue: String): String

    fun get(key: String, defaultValue: Int): Int

    fun get(key: String, defaultValue: Boolean): Boolean

    fun get(key: String, defaultValue: Float): Float

    fun get(key: String, defaultValue: Long): Long

    fun remove(key: String)

    fun removeAll()
}