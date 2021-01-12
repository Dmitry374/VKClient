package com.dima.vkclient.ui.main

import com.dima.vkclient.data.domain.post.PostItem

interface MainView {

    fun showErrorLayout()

    fun manageVisibilityTabFavourites(showFavourites: Boolean)

    fun startShimmerAnimation()

    fun stopShimmerAnimation()

    fun showErrorDialog()

    fun showNewsFragment(updatePositions: List<Int>)

    fun showFavouritesFragment(favouriteNews: List<PostItem>, updatePositions: List<Int>)

    fun showProfileFragment()

    fun showAllNewsIfFavouritesEmpty(favouriteNews: List<PostItem>)

    fun refreshAllData(newsPosts: List<PostItem>, favouriteNews: List<PostItem>)

    fun restoreData(newsPosts: List<PostItem>, favouriteNews: List<PostItem>)

    fun setFirstDataFromDB(newsPosts: List<PostItem>)
}