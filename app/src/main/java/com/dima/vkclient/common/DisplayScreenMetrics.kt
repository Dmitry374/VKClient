package com.dima.vkclient.common

import android.content.Context
import android.content.SharedPreferences
import com.dima.vkclient.R

class DisplayScreenMetrics(context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val DISPLAY_WIDTH = "display_width"
        const val DISPLAY_HEIGHT = "display_height"
    }

    fun saveDisplayMetrics(width: Int, height: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(DISPLAY_WIDTH, width)
        editor.putInt(DISPLAY_HEIGHT, height)
        editor.apply()
    }

    fun getWidth(): Int {
        return sharedPreferences.getInt(DISPLAY_WIDTH, 0)
    }

    fun getHeight(): Int {
        return sharedPreferences.getInt(DISPLAY_HEIGHT, 0)
    }
}