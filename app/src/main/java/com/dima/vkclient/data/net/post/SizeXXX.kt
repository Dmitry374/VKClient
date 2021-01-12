package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SizeXXX(
    val height: Int,
    val type: String,
    val url: String,
    val width: Int
) : Parcelable