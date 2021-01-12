package com.dima.vkclient.data.net.comment

import com.google.gson.annotations.SerializedName

data class CommentItemNet(
    val date: Long,
    @SerializedName("from_id")
    val fromId: Int,
    val id: Int,
    val likes: CommentLikesNet?,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("parents_stack")
    val parentsStack: List<Any>,
    @SerializedName("post_id")
    val postId: Int,
    val text: String?,
    val thread: Thread
)