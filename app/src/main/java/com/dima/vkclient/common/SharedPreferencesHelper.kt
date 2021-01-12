package com.dima.vkclient.common

import android.content.SharedPreferences

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    fun setUserId(userId: Int) = sharedPreferences.edit().putInt(Constants.USER_ID, userId).apply()
    fun getUserId(): Int = sharedPreferences.getInt(Constants.USER_ID, 0)
}