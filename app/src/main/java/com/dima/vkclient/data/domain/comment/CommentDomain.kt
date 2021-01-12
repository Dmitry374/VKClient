package com.dima.vkclient.data.domain.comment

class CommentDomain(
    val id: Int,
    val fromId: Int,
    val postId: Int,
    val ownerId: Int,
    val date: Long,
    val text: String?,
    val likes: LikesCommentDomain?
)