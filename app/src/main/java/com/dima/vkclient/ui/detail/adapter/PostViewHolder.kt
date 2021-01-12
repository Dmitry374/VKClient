package com.dima.vkclient.ui.detail.adapter

import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.dpToPx
import com.dima.vkclient.data.domain.post.PhotoSizeDomain
import com.dima.vkclient.data.domain.post.PostItem

class PostViewHolder(
    itemView: View,
    private val transitionNameContentImage: String?,
    private val contentImage: PhotoSizeDomain?,
    private val listener: PostRowListener
) : RecyclerView.ViewHolder(itemView) {

    fun bind(postItem: PostItem) {

        itemView fillCommunityDetail postItem

        itemView applyContentImage postItem

        itemView fillTextContent postItem

        itemView fillLikeCount postItem

        itemView.findViewById<ImageView>(R.id.saveImageToGallery).setOnClickListener {
            listener.saveImageToGallery()
        }

        itemView.findViewById<ImageView>(R.id.shareImage).setOnClickListener {
            listener.shareImage(contentImage?.url)
        }

    }

    private infix fun View.fillCommunityDetail(postItem: PostItem) {

        val communityAvatarDetail: ImageView = itemView.findViewById(R.id.communityAvatarDetail)

        val avatarUrl =
            when (itemView.context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                Configuration.SCREENLAYOUT_SIZE_SMALL -> {
                    if (postItem.group != null) postItem.group.photo50 else postItem.profile?.photo50
                }
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
                    if (postItem.group != null) postItem.group.photo100 else postItem.profile?.photo100
                }
                Configuration.SCREENLAYOUT_SIZE_LARGE -> {
                    if (postItem.group != null) postItem.group.photo200 else postItem.profile?.photo100
                }
                else -> {
                    if (postItem.group != null) postItem.group.photo200 else postItem.profile?.photo100
                }
            }

        Glide.with(communityAvatarDetail)
            .load(avatarUrl)
            .apply(
                RequestOptions().override(
                    Constants.COMMUNITY_AVATAR_SIZE.dpToPx(),
                    Constants.COMMUNITY_AVATAR_SIZE.dpToPx()
                )
            )
            .circleCrop()
            .into(communityAvatarDetail)

        itemView.findViewById<TextView>(R.id.communityNameDetail).text =
            if (postItem.group != null) {
                postItem.group.name
            } else {
                "${postItem.profile?.firstName} ${postItem.profile?.lastName}"
            }
    }

    private infix fun View.applyContentImage(postItem: PostItem) {

        val postContentImageDetail: ImageView = itemView.findViewById(R.id.postContentImageDetail)

        if (postItem.attachments.isNullOrEmpty() || postItem.attachments.count { it.attachment.type == Constants.ATTACHMENT_TYPE_PHOTO } == 0) {
            postContentImageDetail.visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.saveImageToGallery).visibility = View.GONE
            itemView.findViewById<ImageView>(R.id.shareImage).visibility = View.GONE
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                postContentImageDetail.transitionName = transitionNameContentImage
            }

            contentImage?.let { photo ->
                Glide
                    .with(postContentImageDetail)
                    .load(photo.url)
                    .apply(
                        RequestOptions().override(
                            photo.width,
                            photo.height
                        )
                    )
                    .into(postContentImageDetail)
            }
        }
    }

    private infix fun View.fillTextContent(postItem: PostItem) {

        val postContentTextDetail: TextView = itemView.findViewById(R.id.postContentTextDetail)

        postContentTextDetail.visibility =
            if (postItem.post.text.isEmpty()) View.GONE else View.VISIBLE

        postContentTextDetail.text = postItem.post.text
    }

    private infix fun View.fillLikeCount(postItem: PostItem) {

        val likeCountDetail: TextView = itemView.findViewById(R.id.likeCountDetail)

        likeCountDetail.text = postItem.post.likes.count.toString()
        likeCountDetail.setCompoundDrawablesWithIntrinsicBounds(
            if (postItem.post.likes.userLikes == Constants.IS_LIKED) R.drawable.ic_liked else R.drawable.ic_not_liked,
            0,
            0,
            0
        )
    }
}