package com.dima.vkclient.data.domain.comment

class LikesCommentDomain(
    val canLike: Int,
    val canPublish: Boolean,
    var count: Int,
    var userLikes: Int
)