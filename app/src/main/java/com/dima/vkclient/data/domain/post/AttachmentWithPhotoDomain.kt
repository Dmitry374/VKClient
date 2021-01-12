package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class AttachmentWithPhotoDomain(
    val attachment: AttachmentDomain,
    val photos: List<PhotoSizeDomain>?
) : Parcelable