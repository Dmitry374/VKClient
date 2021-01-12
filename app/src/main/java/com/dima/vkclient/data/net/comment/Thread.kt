package com.dima.vkclient.data.net.comment

import com.google.gson.annotations.SerializedName

data class Thread(
    @SerializedName("can_post")
    val canPost: Boolean,
    val count: Int,
    @SerializedName("groups_can_post")
    val groupsCanPost: Boolean,
    val items: List<CommentItemThreadNet>,
    @SerializedName("show_reply_button")
    val showReplyButton: Boolean
)