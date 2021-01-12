package com.dima.vkclient.ui.detail

import com.dima.vkclient.data.PostWithCommentsAndDiffResult
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel

interface DetailView {

    fun updateCommentsData(comments: List<PostAndCommentUiModel>)

    fun showNewComment(comment: PostAndCommentUiModel)

    fun showCreateCommentError()

    fun showComments(postWithCommentsAndDiffResult: PostWithCommentsAndDiffResult)

    fun showSavedImageMessage()

    fun showErrorSavedImageMessage()

    fun shareImage(imageName: String)

    fun errorShareImage()
}