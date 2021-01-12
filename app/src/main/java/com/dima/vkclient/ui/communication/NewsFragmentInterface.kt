package com.dima.vkclient.ui.communication

import com.dima.vkclient.data.domain.post.PostItem

interface NewsFragmentInterface : TabFragmentInterface {
    fun onUpdatePostsData(list: List<PostItem>)
    fun onRestoreAllData(list: List<PostItem>)
    fun showErrorLayout()
    fun hideErrorLayout()
    fun showErrorDialog()

    fun startShimmerAnimation()
    fun stopShimmerAnimation()
}