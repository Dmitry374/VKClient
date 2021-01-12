package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AttachmentDomain(
    val id: Int,
    val type: String?,
    val photo: PhotoDomain?,
    val postId: Int
) : Parcelable