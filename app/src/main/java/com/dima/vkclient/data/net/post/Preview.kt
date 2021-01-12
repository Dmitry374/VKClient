package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Preview(
    val photo: Photo
) : Parcelable