package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttachmentX(
    val link: LinkX,
    val photo: PhotoXXXX,
    val type: String
) : Parcelable