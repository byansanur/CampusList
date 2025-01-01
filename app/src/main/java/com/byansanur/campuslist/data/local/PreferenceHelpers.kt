package com.byansanur.campuslist.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelpers @Inject constructor(context: Context) {

    companion object {
        private const val PREF_CAMPUS_PACKAGE_NAME = "byansanur.campuslist.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val socialPref: SharedPreferences =
        context.getSharedPreferences(PREF_CAMPUS_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastCacheTime: Long
        get() = socialPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = socialPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}