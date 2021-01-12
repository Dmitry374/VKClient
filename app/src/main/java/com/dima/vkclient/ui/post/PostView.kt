package com.dima.vkclient.ui.post

import com.dima.vkclient.data.domain.post.PostItem

interface PostView {

    fun onErrorHidePost(postItem: PostItem, position: Int)

}