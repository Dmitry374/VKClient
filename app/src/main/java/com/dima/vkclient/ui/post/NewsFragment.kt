package com.dima.vkclient.ui.post

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.dima.vkclient.R
import com.dima.vkclient.ui.post.adapter.PostsListType
import com.dima.vkclient.ui.communication.NewsCommunication

class NewsFragment : AbstractPostFragment(),
    NewsCommunication {

    override fun onUpdateNewsData(list: List<Int>) {
        postsAdapter.update(list)
    }

    override fun getType(): PostsListType = PostsListType.ALL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_posts)
        toolbar.title = resources.getString(R.string.news)

        initRecyclerView()
    }
}