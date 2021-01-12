package com.dima.vkclient.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R
import com.dima.vkclient.ui.post.adapter.PostWithTextAndImageViewHolder
import com.dima.vkclient.ui.post.adapter.PostWithTextViewHolder
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.data.domain.post.PostItem

private const val PROFILE_ROW_TYPE = 0
private const val TYPE_POST_WITH_TEXT = 1
private const val TYPE_POST_WITH_TEXT_AND_IMAGE = 2

class UserProfileAdapter(
    var data: List<ProfileAndPostsUiModel>,
    private val displayScreenMetrics: DisplayScreenMetrics,
    private val clickPostListener: (PostItem, ImageView?, String?) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setDiffResult(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is ProfileAndPostsUiModel.ProfileUiModel -> PROFILE_ROW_TYPE
            is ProfileAndPostsUiModel.PostWithTextUiModel -> TYPE_POST_WITH_TEXT
            is ProfileAndPostsUiModel.PostWithTextAndImageUiModel -> TYPE_POST_WITH_TEXT_AND_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PROFILE_ROW_TYPE -> {
                ProfileViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_type_user_profile, parent, false)
                )
            }
            TYPE_POST_WITH_TEXT -> {
                PostWithTextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post_text_only, parent, false)
                ) { position ->
                    clickPostListener((data[position] as ProfileAndPostsUiModel.PostWithTextUiModel).postItem, null, null)
                }
            }
            TYPE_POST_WITH_TEXT_AND_IMAGE -> {
                PostWithTextAndImageViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post_text_image, parent, false),
                    displayScreenMetrics
                ) { position, imageView, transitionName ->
                    clickPostListener((data[position] as ProfileAndPostsUiModel.PostWithTextAndImageUiModel).postItem, imageView, transitionName)
                }
            }
            else -> throw IllegalArgumentException("Unsupported profile or post view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when (it) {
                is ProfileAndPostsUiModel.ProfileUiModel -> (holder as ProfileViewHolder).bind(it.profileItem)
                is ProfileAndPostsUiModel.PostWithTextUiModel -> (holder as PostWithTextViewHolder).bind(it.postItem)
                is ProfileAndPostsUiModel.PostWithTextAndImageUiModel -> (holder as PostWithTextAndImageViewHolder).bind(it.postItem)
            }
        }
    }
}

class UserProfileDiffCallback(
    oldList: List<ProfileAndPostsUiModel>,
    newList: List<ProfileAndPostsUiModel>
) : DiffUtil.Callback() {

    private val oldList = oldList.toList()
    private val newList = newList.toList()

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition] is ProfileAndPostsUiModel.ProfileUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.ProfileUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.ProfileUiModel).profileItem.id == (newList[newItemPosition] as ProfileAndPostsUiModel.ProfileUiModel).profileItem.id) ||
                (oldList[oldItemPosition] is ProfileAndPostsUiModel.PostWithTextUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.PostWithTextUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.PostWithTextUiModel).postItem.post.id == (newList[newItemPosition] as ProfileAndPostsUiModel.PostWithTextUiModel).postItem.post.id) ||
                (oldList[oldItemPosition] is ProfileAndPostsUiModel.PostWithTextAndImageUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.PostWithTextAndImageUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.PostWithTextAndImageUiModel).postItem.post.id == (newList[newItemPosition] as ProfileAndPostsUiModel.PostWithTextAndImageUiModel).postItem.post.id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] is ProfileAndPostsUiModel.ProfileUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.ProfileUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.ProfileUiModel).profileItem == (newList[newItemPosition] as ProfileAndPostsUiModel.ProfileUiModel).profileItem ||
                oldList[oldItemPosition] is ProfileAndPostsUiModel.PostWithTextUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.PostWithTextUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.PostWithTextUiModel).postItem == (newList[newItemPosition] as ProfileAndPostsUiModel.PostWithTextUiModel).postItem ||
                oldList[oldItemPosition] is ProfileAndPostsUiModel.PostWithTextAndImageUiModel && newList[newItemPosition] is ProfileAndPostsUiModel.PostWithTextAndImageUiModel && (oldList[oldItemPosition] as ProfileAndPostsUiModel.PostWithTextAndImageUiModel).postItem == (newList[newItemPosition] as ProfileAndPostsUiModel.PostWithTextAndImageUiModel).postItem
    }
}