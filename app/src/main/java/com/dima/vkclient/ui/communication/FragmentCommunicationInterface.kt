package com.dima.vkclient.ui.communication

import android.widget.ImageView
import com.dima.vkclient.data.PostsItemAndProfileUiModel
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.ui.post.adapter.PostsListType

interface FragmentCommunicationInterface {
    fun onSendPostsList(postsList: List<PostItem>, postsListType: PostsListType, postItem: PostItem? = null)
    fun onOpenPostDetail(postItem: PostItem, contentImageView: ImageView? = null, transitionName: String? = null)
    fun onCreatePost()

    fun loadNews()

    fun addLike(postItem: PostItem)
    fun deleteLike(postItem: PostItem)

    fun shareImage(imageName: String)

    fun displayNewPost(postsItemAndProfileUiModel: PostsItemAndProfileUiModel)
}