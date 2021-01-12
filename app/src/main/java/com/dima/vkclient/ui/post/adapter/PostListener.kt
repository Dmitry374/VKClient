package com.dima.vkclient.ui.post.adapter

import com.dima.vkclient.data.domain.post.PostItem

interface PostListener {

    fun addLike(postItem: PostItem)

    fun deleteLike(postItem: PostItem)

    fun onHidePost(postItem: PostItem, position: Int)

    fun removePost(position: Int)

    fun onSendPostsListToActivity(postsListType: PostsListType, postItem: PostItem)
}