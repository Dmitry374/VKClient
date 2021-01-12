package com.dima.vkclient.ui.post.adapter

import android.os.Build
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.data.domain.post.PhotoSizeDomain
import com.dima.vkclient.data.domain.post.PostItem

class PostWithTextAndImageViewHolder(
    itemView: View,
    private val displayScreenMetrics: DisplayScreenMetrics,
    private val onPostClick: (Int, ImageView?, String?) -> Unit
) :
    AbstractPostViewHolder(itemView) {

    override fun bind(postItem: PostItem) {
        super.bind(postItem)

        val imageContent = itemView.findViewById<ImageView>(R.id.postContentImage)

        val screenWidth = displayScreenMetrics.getWidth()

        postItem.attachments?.find { attachment -> attachment.attachment.type == Constants.ATTACHMENT_TYPE_PHOTO }
            ?.let { attachment ->

                var photoWithSize: PhotoSizeDomain? = null
                attachment.photos?.forEach { size ->
                    if (size.width <= screenWidth) {
                        photoWithSize = size
                    }
                }

                photoWithSize?.let { photo ->
                    Glide
                        .with(imageContent)
                        .load(photo.url)
                        .apply(
                            RequestOptions().override(
                                photo.width,
                                photo.height
                            )
                        )
                        .into(imageContent)
                }
            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageContent.transitionName = String.format(
                itemView.context.getString(R.string.transition_name_template),
                adapterPosition
            )
        }

        itemView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                onPostClick(adapterPosition, imageContent, imageContent.transitionName)
            } else {
                onPostClick(adapterPosition, null, null)
            }
        }
    }
}