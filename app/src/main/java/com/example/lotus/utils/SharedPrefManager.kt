package com.example.lotus.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.lotus.ui.adapter.dataItem.ItemProfile

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREF_NAME = "my_shared_pref"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_TOKEN = "token"
    }

    fun saveLoginState(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun saveUserId(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userId", id)
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("userId", null)
    }

    fun clearLoginState() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_TOKEN)
        editor.remove("userId")
        editor.apply()
    }
}
