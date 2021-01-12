package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Size(
    val height: Int,
    val src: String,
    val type: String,
    val width: Int
) : Parcelable