package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Price(
    val amount: String,
    val currency: Currency,
    val text: String
) : Parcelable