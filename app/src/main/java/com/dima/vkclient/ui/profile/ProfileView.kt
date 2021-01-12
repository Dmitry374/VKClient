package com.dima.vkclient.ui.profile

import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel

interface ProfileView {

    fun showErrorDialog()

    fun startShimmerAnimation()

    fun stopShimmerAnimation()

    fun displayProfile(profileUiModel: ProfileAndPostsUiModel)

    fun displayWallPosts(posts: List<ProfileAndPostsUiModel>)

}