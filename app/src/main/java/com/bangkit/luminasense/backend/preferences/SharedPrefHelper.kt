package com.bangkit.luminasense.backend.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val prefsName = "myAppPrefs"
    private val tokenKey = "token"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(tokenKey, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(tokenKey, null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove(tokenKey)
        editor.apply()
    }
}
