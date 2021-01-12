package com.dima.vkclient.ui.communication

import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel

interface UserProfileCommunication : TabFragmentInterface {
    fun showErrorLayout()
    fun hideErrorLayout()
    fun loadUserProfileDataAndWallPosts()
    fun displayNewPost(profileAndPostsUiModel: ProfileAndPostsUiModel)
}