package com.dima.vkclient.ui.post

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.dima.vkclient.R
import com.dima.vkclient.ui.post.adapter.PostsListType
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.ui.communication.FavouriteCommunication

class FavouriteFragment : AbstractPostFragment(),
    FavouriteCommunication {

    override fun onUpdateFavouriteNewsData(list: List<PostItem>, refreshPosition: List<Int>) {
        postViewModel.posts = list as ArrayList
        postViewModel.updateData(postsAdapter.postItems, list)
        postsAdapter.update(refreshPosition)
    }

    override fun getType(): PostsListType = PostsListType.FAVOURITE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_posts)
        toolbar.title = resources.getString(R.string.favourite)

        initRecyclerView()
    }
}