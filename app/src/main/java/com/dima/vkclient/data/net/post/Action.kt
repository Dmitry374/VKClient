package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Action(
    val target: String,
    val type: String,
    val url: String
) : Parcelable