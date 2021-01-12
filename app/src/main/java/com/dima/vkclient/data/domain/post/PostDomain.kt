package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PostDomain(
    val id: Int,
    val comments: CommentsDomain,
    val date: Long,
    val isFavorite: Boolean,
    val likes: LikesDomain,
    val markedAsAds: Int,
    val postType: String,
    val reposts: RepostsDomain,
    val sourceId: Int,
    val sourceIdAbs: Int,
    val text: String,
    val type: String,
    val views: ViewsDomain?
) : Parcelable