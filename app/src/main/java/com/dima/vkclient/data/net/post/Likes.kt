package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Likes(
    @SerializedName("can_like")
    val canLike: Int,
    @SerializedName("can_publish")
    val canPublish: Int,
    var count: Int,
    @SerializedName("user_likes")
    var userLikes: Int
) : Parcelable