package com.dima.vkclient.data.net.comment

import com.google.gson.annotations.SerializedName

data class CommentItemThreadNet(
    val date: Int,
    @SerializedName("from_id")
    val fromId: Int,
    val id: Int,
    val likes: CommentLikesNet,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("parents_stack")
    val parentsStack: List<Int>,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("reply_to_comment")
    val replyToComment: Int,
    @SerializedName("reply_to_user")
    val replyToUser: Int,
    val text: String
)