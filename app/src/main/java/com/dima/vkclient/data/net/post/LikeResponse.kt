package com.dima.vkclient.data.net.post

data class LikeResponse(
    val response: ResponseLikeCount
)

data class ResponseLikeCount(
    val likes: Int
)