package com.dima.vkclient.ui.createpost

import com.dima.vkclient.data.PostsItemAndProfileUiModel

interface CreatePostView {

    fun showErrorDialog()

    fun displayNewPost(postsItemAndProfileUiModel: PostsItemAndProfileUiModel)

}