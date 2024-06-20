package com.bangkit.luminasense.backend.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val prefsName = "myAppPrefs"
    private val tokenKey = "token"
    private val ipKey = "ip_address"
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

    fun saveIpAddress(ip: String) {
        val editor = sharedPreferences.edit()
        editor.putString(ipKey, ip)
        editor.apply()
    }

    fun getIpAddress(): String? {
        return sharedPreferences.getString(ipKey, null)
    }

    fun clearIpAddress() {
        val editor = sharedPreferences.edit()
        editor.remove(ipKey)
        editor.apply()
    }
}
