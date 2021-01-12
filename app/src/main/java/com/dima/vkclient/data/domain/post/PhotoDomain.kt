package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoDomain(
    val accessKey: String?,
    val date: Long,
    val hasTags: Boolean,
    val photoId: Int,
    val text: String,
    val userId: Int
) : Parcelable