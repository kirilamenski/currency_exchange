package com.ansgar.domain

import com.ansgar.data.shared_pref.CurrencyExSharedPreferencesImpl
import javax.inject.Inject

class GetSharedPreferenceValue @Inject constructor(
    private val sharedPreferences: CurrencyExSharedPreferencesImpl?
) {

    fun get(key: String, defaultValue: String): String =
        sharedPreferences?.get(key, defaultValue) ?: defaultValue

    fun get(key: String, defaultValue: Int): Int =
        sharedPreferences?.get(key, defaultValue) ?: defaultValue

    fun get(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences?.get(key, defaultValue) ?: defaultValue

    fun get(key: String, defaultValue: Float): Float =
        sharedPreferences?.get(key, defaultValue) ?: defaultValue

    fun get(key: String, defaultValue: Long): Long =
        sharedPreferences?.get(key, defaultValue) ?: defaultValue

}