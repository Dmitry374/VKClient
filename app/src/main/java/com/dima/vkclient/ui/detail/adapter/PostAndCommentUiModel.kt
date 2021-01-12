package com.dima.vkclient.ui.detail.adapter

import com.dima.vkclient.data.domain.comment.CommentItem
import com.dima.vkclient.data.domain.post.PostItem

sealed class PostAndCommentUiModel {
    data class PostUiModel(val postItem: PostItem) : PostAndCommentUiModel()
    data class CommentUiModel(val commentItem: CommentItem) : PostAndCommentUiModel()
}