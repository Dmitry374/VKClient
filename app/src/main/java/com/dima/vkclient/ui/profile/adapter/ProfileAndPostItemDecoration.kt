package com.dima.vkclient.ui.profile.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R

class ProfileAndPostItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom =
            parent.context.resources.getDimensionPixelSize(R.dimen.margin_between_posts)
    }
}