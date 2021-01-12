package com.dima.vkclient.ui.post.adapter

import android.view.View

class PostWithTextViewHolder(itemView: View, onPostClick: (Int) -> Unit) :
    AbstractPostViewHolder(itemView) {

    init {
        itemView.setOnClickListener { onPostClick(adapterPosition) }
    }
}