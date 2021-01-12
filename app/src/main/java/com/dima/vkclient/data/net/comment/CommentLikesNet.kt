package com.dima.vkclient.data.net.comment

import com.google.gson.annotations.SerializedName

data class CommentLikesNet(
    @SerializedName("can_like")
    val canLike: Int,
    @SerializedName("can_publish")
    val canPublish: Boolean,
    val count: Int,
    @SerializedName("user_likes")
    val userLikes: Int
)