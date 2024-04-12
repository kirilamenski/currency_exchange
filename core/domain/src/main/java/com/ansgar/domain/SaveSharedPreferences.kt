package com.ansgar.domain

import com.ansgar.data.shared_pref.CurrencyExSharedPreferencesImpl
import javax.inject.Inject

class SaveSharedPreferences @Inject constructor(
    private val sharedPreferences: CurrencyExSharedPreferencesImpl?
) {

    suspend operator fun invoke(key: String, value: Any) {
        sharedPreferences?.put(key = key, value = value)
    }
}
