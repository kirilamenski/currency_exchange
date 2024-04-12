package com.ansgar.data.shared_pref

import android.content.SharedPreferences
import timber.log.Timber
import javax.inject.Inject

class CurrencyExSharedPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences?
) : CurrencyExSharedPreferences {

    override fun put(key: String, value: Any) {
        sharedPreferences?.run {
            when (value) {
                is String -> edit().putString(key, value).apply()
                is Boolean -> edit().putBoolean(key, value).apply()
                is Int -> edit().putInt(key, value).apply()
                is Float -> edit().putFloat(key, value).apply()
                is Long -> edit().putLong(key, value).apply()
                else -> Timber.e("${value::class.java.simpleName} are not supported")
            }
        }
    }

    override fun get(key: String, defaultValue: String): String =
        sharedPreferences?.getString(key, defaultValue) ?: defaultValue

    override fun get(key: String, defaultValue: Int): Int =
        sharedPreferences?.getInt(key, defaultValue) ?: defaultValue

    override fun get(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences?.getBoolean(key, defaultValue) ?: defaultValue

    override fun get(key: String, defaultValue: Float): Float =
        sharedPreferences?.getFloat(key, defaultValue) ?: defaultValue

    override fun get(key: String, defaultValue: Long): Long =
        sharedPreferences?.getLong(key, defaultValue) ?: defaultValue

    override fun remove(key: String) {
        sharedPreferences?.run { edit().remove(key).apply() }
    }

    override fun removeAll() {
        sharedPreferences?.run { edit().clear().apply() }
    }
}