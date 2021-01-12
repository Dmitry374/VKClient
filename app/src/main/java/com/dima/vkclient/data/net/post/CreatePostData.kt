package com.dima.vkclient.data.net.post

import com.google.gson.annotations.SerializedName

data class CreatePostData(
    val response: CreatePostResponse
)

data class CreatePostResponse(
    @SerializedName("post_id")
    val postId: Int
)