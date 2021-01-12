package com.dima.vkclient.common

import java.text.SimpleDateFormat
import java.util.*

fun Long.getDateTextForHeader(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("dd-MMMM-yyyy", Locale("ru"))
    return format.format(date)
}

fun Long.getDateTimeText(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("ru"))
    return format.format(date)
}

fun Long.getDateTimeWholeDayText(): String {
    val date = Date(this * 1000)
    val format = SimpleDateFormat("d MMMM yyyy HH:mm", Locale("ru"))
    return format.format(date)
}

fun Long.getDateOnly(): Date? {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
    val date = Date(this * 1000)

    return formatter.parse(formatter.format(date))
}

fun Long.isToday(): Boolean {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
    val today = Date()
    val date = Date(this * 1000)

    return formatter.parse(formatter.format(date)) == formatter.parse(formatter.format(today))
}

fun Long.isYesterday(): Boolean {
    val c1 = Calendar.getInstance()
    c1.add(Calendar.DAY_OF_YEAR, -1)

    val c2 = Calendar.getInstance()
    c2.timeInMillis = this * 1000

    return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
            && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
}