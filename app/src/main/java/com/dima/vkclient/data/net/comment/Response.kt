package com.dima.vkclient.data.net.comment

import com.dima.vkclient.data.net.post.Group
import com.dima.vkclient.data.net.post.Profile
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("can_post")
    val canPost: Boolean,
    val count: Int,
    @SerializedName("current_level_count")
    val currentLevelCount: Int,
    val groups: List<Group>,
    @SerializedName("groups_can_post")
    val groupsCanPost: Boolean,
    val items: List<CommentItemNet>,
    val profiles: List<Profile>,
    @SerializedName("show_reply_button")
    val showReplyButton: Boolean
)