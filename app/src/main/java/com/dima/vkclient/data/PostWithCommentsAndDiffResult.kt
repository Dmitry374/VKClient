package com.dima.vkclient.data

import androidx.recyclerview.widget.DiffUtil
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel

class PostWithCommentsAndDiffResult(
    val newList: List<PostAndCommentUiModel>,
    val diffResult: DiffUtil.DiffResult
)