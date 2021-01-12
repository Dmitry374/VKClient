package com.dima.vkclient.common

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun View.getWidthWithMargins(): Int {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    return this.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
}

fun View.getHeightWithMargins(): Int {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    return this.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
}

fun Int.dpToPx(): Int {
    val resources = Resources.getSystem()
    val displayMetrics = resources.displayMetrics
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        displayMetrics
    ).toInt()
}

fun EditText.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}