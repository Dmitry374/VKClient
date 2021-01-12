package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostItem(
    val post: PostDomain,
    val group: GroupDomain?,
    val profile: ProfileDomain?,
    val attachments: List<AttachmentWithPhotoDomain>?
) : Parcelable