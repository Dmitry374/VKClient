package com.dima.vkclient.data.net.comment

import com.google.gson.annotations.SerializedName

data class CreateCommentResponse(
    val response: ResponseCreatedComment
)

data class ResponseCreatedComment(
    @SerializedName("comment_id")
    val commentId: Int,
    @SerializedName("parents_stack")
    val parentsStack: List<Any>
)