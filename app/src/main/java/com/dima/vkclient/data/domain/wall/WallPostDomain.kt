package com.dima.vkclient.data.domain.wall

import com.dima.vkclient.data.domain.post.CommentsDomain
import com.dima.vkclient.data.domain.post.LikesDomain
import com.dima.vkclient.data.domain.post.ViewsDomain

class WallPostDomain(
    val id: Int,
    val fromId: Int,
    val ownerId: Int,
    val ownerIdAbs: Int,
    val date: Long,
    val postType: String,
    val text: String,
    val canEdit: Int,
    val canDelete: Int,
    val canPin: Int,
    val canArchive: Boolean,
    val isArchived: Boolean,
    val comments: CommentsDomain,
    val likes: LikesDomain,
    val reposts: WallRepostsDomain,
    val isFavorite: Boolean,
    val shortTextRate: Double,
    val views: ViewsDomain?
)