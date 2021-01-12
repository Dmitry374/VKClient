package com.dima.vkclient.ui.detail.adapter

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
import com.dima.vkclient.data.domain.comment.CommentItem

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(commentItem: CommentItem) {

        itemView fillUserDetail commentItem

        itemView fillCommentTextAndDate commentItem

        itemView fillCommentLikes commentItem

    }

    private infix fun View.fillUserDetail(commentItem: CommentItem) {

        val commentAvatarUser = itemView.findViewById<ImageView>(R.id.commentAvatarUser)

        itemView.findViewById<TextView>(R.id.userName).text = if (commentItem.profile != null) {
            "${commentItem.profile.firstName} ${commentItem.profile.lastName}"
        } else {
            commentItem.group?.name
        }

        val avatarUrl =
            when (itemView.context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                Configuration.SCREENLAYOUT_SIZE_SMALL -> {
                    if (commentItem.profile != null) commentItem.profile.photo50 else commentItem.group?.photo50
                }
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
                    if (commentItem.profile != null) commentItem.profile.photo100 else commentItem.group?.photo100
                }
                Configuration.SCREENLAYOUT_SIZE_LARGE -> {
                    if (commentItem.profile != null) commentItem.profile.photo100 else commentItem.group?.photo200
                }
                else -> {
                    if (commentItem.profile != null) commentItem.profile.photo100 else commentItem.group?.photo200
                }
            }

        Glide.with(commentAvatarUser)
            .load(avatarUrl)
            .apply(
                RequestOptions().override(
                    Constants.COMMUNITY_AVATAR_SIZE.dpToPx(),
                    Constants.COMMUNITY_AVATAR_SIZE.dpToPx()
                )
            )
            .circleCrop()
            .into(commentAvatarUser)
    }

    private infix fun View.fillCommentTextAndDate(commentItem: CommentItem) {

        itemView.findViewById<TextView>(R.id.commentText).text = commentItem.comment.text

        itemView.findViewById<TextView>(R.id.commentDate).text =
            commentItem.comment.date.getDateTimeText()
    }

    private infix fun View.fillCommentLikes(commentItem: CommentItem) {

        itemView.findViewById<TextView>(R.id.commentLikes).text =
            if (commentItem.comment.likes == null || commentItem.comment.likes.count == 0) {
                ""
            } else {
                commentItem.comment.likes.count.toString()
            }
    }
}