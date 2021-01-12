package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoSizeDomain(
    val id: Long = 0L,
    val idAttachmentPhoto: Int,
    val height: Int,
    val type: String,
    val url: String,
    val width: Int
) : Parcelable