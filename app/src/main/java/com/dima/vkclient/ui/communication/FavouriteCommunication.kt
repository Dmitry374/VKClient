package com.dima.vkclient.ui.communication

import com.dima.vkclient.data.domain.post.PostItem

interface FavouriteCommunication : NewsFragmentInterface {
    fun onUpdateFavouriteNewsData(list: List<PostItem>, refreshPosition: List<Int>)
}