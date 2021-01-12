package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
) : Parcelable