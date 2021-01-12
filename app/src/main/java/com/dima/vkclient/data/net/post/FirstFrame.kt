package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FirstFrame(
    val height: Int,
    val url: String,
    val width: Int
) : Parcelable