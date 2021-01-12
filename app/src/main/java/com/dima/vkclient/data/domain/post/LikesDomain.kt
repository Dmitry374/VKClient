package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LikesDomain(
    val canLike: Int,
    val canPublish: Int,
    var count: Int,
    var userLikes: Int
) : Parcelable