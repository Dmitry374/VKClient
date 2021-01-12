package com.dima.vkclient.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R
import com.dima.vkclient.data.domain.post.PhotoSizeDomain

private const val POST_ROW_TYPE = 0
private const val COMMENT_ROW_TYPE = 1

class CommentsAdapter(
    var data: List<PostAndCommentUiModel>,
    private val transitionNameContentImage: String?,
    private val contentImage: PhotoSizeDomain?,
    private val listener: PostRowListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is PostAndCommentUiModel.PostUiModel -> POST_ROW_TYPE
            is PostAndCommentUiModel.CommentUiModel -> COMMENT_ROW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            POST_ROW_TYPE -> {
                PostViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_type_post, parent, false),
                    transitionNameContentImage = transitionNameContentImage,
                    contentImage = contentImage,
                    listener = listener
                )
            }
            COMMENT_ROW_TYPE -> {
                CommentViewHolder(
                    itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_type_comment, parent, false)
                )
            }
            else -> throw IllegalArgumentException("Unsupported post or comment view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when (it) {
                is PostAndCommentUiModel.PostUiModel -> (holder as PostViewHolder).bind(it.postItem)
                is PostAndCommentUiModel.CommentUiModel -> (holder as CommentViewHolder).bind(it.commentItem)
            }
        }
    }
}

class CommentDiffCallback(
    oldList: List<PostAndCommentUiModel>,
    newList: List<PostAndCommentUiModel>
) :
    DiffUtil.Callback() {

    private val oldList = oldList.toList()
    private val newList = newList.toList()

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] is PostAndCommentUiModel.PostUiModel && newList[newItemPosition] is PostAndCommentUiModel.PostUiModel &&
                (oldList[oldItemPosition] as PostAndCommentUiModel.PostUiModel).postItem.post.id == (newList[newItemPosition] as PostAndCommentUiModel.PostUiModel).postItem.post.id ||
                oldList[oldItemPosition] is PostAndCommentUiModel.CommentUiModel && newList[newItemPosition] is PostAndCommentUiModel.CommentUiModel &&
                (oldList[oldItemPosition] as PostAndCommentUiModel.CommentUiModel).commentItem.comment.id == (newList[newItemPosition] as PostAndCommentUiModel.CommentUiModel).commentItem.comment.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] is PostAndCommentUiModel.PostUiModel && newList[newItemPosition] is PostAndCommentUiModel.PostUiModel &&
                (oldList[oldItemPosition] as PostAndCommentUiModel.PostUiModel).postItem == (newList[newItemPosition] as PostAndCommentUiModel.PostUiModel).postItem ||
                oldList[oldItemPosition] is PostAndCommentUiModel.CommentUiModel && newList[newItemPosition] is PostAndCommentUiModel.CommentUiModel &&
                (oldList[oldItemPosition] as PostAndCommentUiModel.CommentUiModel).commentItem == (newList[newItemPosition] as PostAndCommentUiModel.CommentUiModel).commentItem
    }
}