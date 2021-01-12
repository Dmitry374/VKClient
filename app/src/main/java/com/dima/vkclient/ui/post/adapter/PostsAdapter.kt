package com.dima.vkclient.ui.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.data.domain.post.PostItem

private const val TYPE_POST_WITH_TEXT_AND_IMAGE = 0
private const val TYPE_POST_WITH_TEXT = 1

class PostsAdapter(
    var postItems: MutableList<PostItem>,
    private val sectionCallback: PostItemDecoration.SectionCallback,
    private val postsListType: PostsListType,
    private val displayScreenMetrics: DisplayScreenMetrics,
    private val postListener: PostListener,
    private val clickPostListener: (PostItem, ImageView?, String?) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PostItemTouchHelperAdapter {

    companion object {
        const val ATTACHMENT_TYPE_PHOTO = "photo"
    }

    fun setDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onItemDismiss(position: Int) {

        val postItem = postItems[position]

        // Обновляем предыдущий элемент в секции чтобы убрать отступ
        if (!sectionCallback.isNotLastItemInSection(position)) {
            notifyItemChanged(position - 1)
        }

        when (postsListType) {
            PostsListType.ALL -> {
                notifyItemRemoved(position)
                postListener.onHidePost(postItem, position)
            }
            PostsListType.FAVOURITE -> {

                postListener.deleteLike(postItem)

                // Обновляем предыдущий элемент в секции чтобы убрать отступ
                if (!sectionCallback.isNotLastItemInSection(position)
                    && !sectionCallback.isSection(position)
                ) {
                    notifyItemChanged(position - 1)
                }

                postListener.removePost(position)
                notifyItemRemoved(position)

                postListener.onSendPostsListToActivity(postsListType, postItem)
            }
        }
    }

    override fun onItemLike(position: Int) {
        val postItem = postItems[position]

        if (postItem.post.likes.userLikes == Constants.IS_LIKED) {
            postListener.deleteLike(postItem)
        } else {
            postListener.addLike(postItem)
        }

        when (postsListType) {
            PostsListType.ALL -> {
                notifyItemChanged(position)
            }
            PostsListType.FAVOURITE -> {
                if (postItem.post.likes.userLikes == Constants.NOT_LIKED) {

                    // Обновляем предыдущий элемент в секции чтобы убрать отступ
                    if (!sectionCallback.isNotLastItemInSection(position)
                        && !sectionCallback.isSection(position)
                    ) {
                        notifyItemChanged(position - 1)
                    }

                    postListener.removePost(position)
                    notifyItemRemoved(position)
                }
            }
        }

        postListener.onSendPostsListToActivity(postsListType, postItem)
    }

    fun update(list: List<Int>) {
        list.forEach { notifyItemChanged(it) }
    }

    override fun getItemCount(): Int = postItems.size

    override fun getItemViewType(position: Int): Int {

        val post = postItems[position]

        return if (post.attachments.isNullOrEmpty() || post.attachments.count { it.attachment.type == ATTACHMENT_TYPE_PHOTO } == 0) {
            TYPE_POST_WITH_TEXT
        } else {
            TYPE_POST_WITH_TEXT_AND_IMAGE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_POST_WITH_TEXT_AND_IMAGE -> {
                PostWithTextAndImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post_text_image, parent, false),
                    displayScreenMetrics
                ) { position, imageView, transitionName ->
                    clickPostListener(postItems[position], imageView, transitionName)
                }
            }
            TYPE_POST_WITH_TEXT -> {
                PostWithTextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post_text_only, parent, false)
                ) { position ->
                    clickPostListener(postItems[position], null, null)
                }
            }
            else -> throw IllegalArgumentException("Unsupported view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = postItems[position]

        when (holder) {
            is PostWithTextAndImageViewHolder -> holder.bind(post)
            is PostWithTextViewHolder -> holder.bind(post)
        }
    }
}

class PostDiffCallback(oldList: List<PostItem>, newList: List<PostItem>) : DiffUtil.Callback() {

    private val oldList = oldList.toList()
    private val newList = newList.toList()

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].post.id == newList[newItemPosition].post.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}