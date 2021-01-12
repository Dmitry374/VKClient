package com.dima.vkclient.ui.profile.adapter

import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.data.domain.userprofile.UserProfileItem

sealed class ProfileAndPostsUiModel {
    data class ProfileUiModel(val profileItem: UserProfileItem) : ProfileAndPostsUiModel()
    data class PostWithTextUiModel(val postItem: PostItem) : ProfileAndPostsUiModel()
    data class PostWithTextAndImageUiModel(val postItem: PostItem) : ProfileAndPostsUiModel()
}