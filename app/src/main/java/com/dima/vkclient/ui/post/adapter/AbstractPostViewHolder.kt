package com.dima.vkclient.ui.post.adapter

import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.dpToPx
import com.dima.vkclient.common.getDateTimeText
import com.dima.vkclient.data.domain.post.PostItem

abstract class AbstractPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        const val COMMUNITY_AVATAR_SIZE = 50
    }

    open fun bind(postItem: PostItem) {
        itemView fillCommunityDetailWith postItem

        itemView.findViewById<TextView>(R.id.postDate).text = postItem.post.date.getDateTimeText()

        itemView fillTextContentWith postItem

        itemView.findViewById<TextView>(R.id.likeCount).text = postItem.post.likes.count.toString()
        itemView.findViewById<TextView>(R.id.likeCount).setCompoundDrawablesWithIntrinsicBounds(
            if (postItem.post.likes.userLikes == Constants.IS_LIKED) R.drawable.ic_liked else R.drawable.ic_not_liked,
            0,
            0,
            0
        )

        if (postItem.post.comments.canPost != Constants.CAN_CREATE_COMMENT) {
            itemView.findViewById<TextView>(R.id.commentCount).visibility = View.GONE
        } else {
            itemView.findViewById<TextView>(R.id.commentCount).text =
                postItem.post.comments.count.toString()
        }

        itemView.findViewById<TextView>(R.id.shareCount).text =
            postItem.post.reposts.count.toString()
    }

    private infix fun View.fillCommunityDetailWith(postItem: PostItem) {

        itemView.findViewById<TextView>(R.id.communityName).text = if (postItem.group != null) {
            postItem.group.name
        } else {
            "${postItem.profile?.firstName} ${postItem.profile?.lastName}"
        }

        val avatarUrl =
            when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
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

        val communityAvatar = itemView.findViewById<ImageView>(R.id.communityAvatar)

        Glide.with(communityAvatar.context)
            .load(avatarUrl)
            .apply(
                RequestOptions().override(
                    COMMUNITY_AVATAR_SIZE.dpToPx(),
                    COMMUNITY_AVATAR_SIZE.dpToPx()
                )
            )
            .circleCrop()
            .into(communityAvatar)
    }

    private infix fun View.fillTextContentWith(postItem: PostItem) {
        val textContent = itemView.findViewById<TextView>(R.id.postContentText)
        textContent.visibility = if (postItem.post.text.isEmpty()) View.GONE else View.VISIBLE
        textContent.text = postItem.post.text
    }
}