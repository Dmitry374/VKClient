package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LinkX(
    val description: String,
    val photo: PhotoXXX,
    val title: String,
    val url: String
) : Parcelable