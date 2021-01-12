package com.dima.vkclient.common

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(
    private val context: Context
) {
    fun getString(@StringRes resId: Int): String = context.getString(resId)
}