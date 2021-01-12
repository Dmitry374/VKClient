package com.dima.vkclient.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.dima.vkclient.R
import com.dima.vkclient.common.dpToPx
import com.dima.vkclient.common.getHeightWithMargins
import com.dima.vkclient.common.getWidthWithMargins
import kotlinx.android.synthetic.main.view_post_text_only.view.*

class PostWithTextOnly @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_post_text_only, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0

        children.forEach { view ->
            measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, height)

            when (view) {
                communityAvatar, likeCount -> height += view.getHeightWithMargins()
                postContentText -> {
                    if (view.visibility != View.GONE) {
                        height += view.measuredHeight + view.marginTop
                    }
                }
            }
        }

        setMeasuredDimension(desiredWidth, View.resolveSize(height, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentLeft = paddingLeft
        var currentTop = paddingTop

        communityAvatar.layout(
            currentLeft + communityAvatar.marginLeft,
            currentTop + communityAvatar.marginTop,
            currentLeft + communityAvatar.measuredWidth + communityAvatar.marginLeft,
            currentTop + communityAvatar.measuredHeight + communityAvatar.marginTop
        )

        currentTop = communityAvatar.measuredHeight / 2 - communityName.measuredHeight

        communityName.layout(
            currentLeft + communityAvatar.getWidthWithMargins(),
            currentTop + communityName.marginTop,
            currentLeft + communityAvatar.getWidthWithMargins() + communityName.measuredWidth,
            currentTop + communityName.measuredHeight + communityName.marginBottom
        )

        currentTop += communityName.measuredHeight

        postDate.layout(
            currentLeft + communityAvatar.getWidthWithMargins(),
            currentTop + postDate.marginTop,
            currentLeft + communityAvatar.getWidthWithMargins() + postDate.measuredWidth,
            currentTop + postDate.measuredHeight + postDate.marginBottom
        )

        currentTop = communityAvatar.getHeightWithMargins()

        if (postContentText.visibility != View.GONE) {
            postContentText.layout(
                currentLeft + postContentText.marginLeft,
                currentTop,
                currentLeft + postContentText.marginLeft + postContentText.measuredWidth,
                currentTop + postContentText.measuredHeight + postContentText.marginTop
            )

            currentTop += postContentText.measuredHeight + postContentText.marginTop
        }

        likeCount.layout(
            currentLeft + likeCount.marginLeft,
            currentTop + likeCount.marginTop,
            currentLeft + likeCount.measuredWidth + likeCount.marginLeft,
            currentTop + likeCount.measuredHeight + likeCount.marginBottom
        )

        currentLeft += likeCount.getWidthWithMargins() + 30.dpToPx()

        if (commentCount.visibility != View.GONE) {
            commentCount.layout(
                currentLeft,
                currentTop + commentCount.marginTop,
                currentLeft + commentCount.measuredWidth,
                currentTop + commentCount.measuredHeight + commentCount.marginBottom
            )

            currentLeft += commentCount.getWidthWithMargins() + 30.dpToPx()
        }

        shareCount.layout(
            currentLeft,
            currentTop + shareCount.marginTop,
            currentLeft + shareCount.measuredWidth,
            currentTop + shareCount.measuredHeight + shareCount.marginBottom
        )

    }

    override fun generateLayoutParams(attrs: AttributeSet) =
        MarginLayoutParams(context, attrs)

    override fun generateDefaultLayoutParams() =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

}