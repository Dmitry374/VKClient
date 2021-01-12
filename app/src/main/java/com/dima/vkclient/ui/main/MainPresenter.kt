package com.dima.vkclient.ui.main

import com.dima.vkclient.ui.post.adapter.PostsListType
import com.dima.vkclient.common.Constants
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.base.presenter.RxPresenter

class MainPresenter(
    private val newsInteractor: NewsInteractor
) : RxPresenter<MainView>(MainView::class.java) {

    private var newsPosts = arrayListOf<PostItem>()

    private var favouriteNews = arrayListOf<PostItem>()

    private var itemsPositionForUpdatesNews = mutableListOf<Int>()
    private var itemsPositionForUpdatesFavourite = mutableListOf<Int>()

    fun loadNews(filters: String, isRefreshing: Boolean) {

        if (isRefreshing) {
            view.startShimmerAnimation()
        }

        newsInteractor.loadNews(filters)
            .subscribe({

                getPostsFromDB(isRefreshing)

            }, {
                getPostsFromDB(isRefreshing)
            })
            .disposeOnFinish()
    }

    fun getPostsFromDB(isRefreshing: Boolean) {

        newsInteractor.getPostsLocal()
            .subscribe({ newsAndFavouriteLists ->

                if (newsAndFavouriteLists.newsList.isEmpty()) {
                    view.showErrorLayout()
                } else {
                    newsPosts = newsAndFavouriteLists.newsList as ArrayList
                    favouriteNews = newsAndFavouriteLists.favouriteNews as ArrayList

                    manageVisibilityTabFavourites()

                    if (isRefreshing) {
                        view.refreshAllData(newsPosts, favouriteNews)

                        view.stopShimmerAnimation()
                    } else {
                        view.setFirstDataFromDB(newsPosts)
                    }
                }

            }, {
                view.showErrorLayout()
            })
            .disposeOnFinish()
    }

    fun addLike(postItem: PostItem) {
        newsInteractor.addLike(postItem)
            .subscribe({
                updatePostInDb(postItem)
            }, {

                rollBackLike()
            })
            .disposeOnFinish()
    }

    fun deleteLike(postItem: PostItem) {
        newsInteractor.deleteLike(postItem)
            .subscribe({
                updatePostInDb(postItem)
            }, {

                rollBackLike()
            })
            .disposeOnFinish()
    }

    private fun updatePostInDb(postItem: PostItem) {
        newsInteractor.updatePostInDb(postItem)
            .subscribe()
            .disposeOnFinish()
    }

    private fun rollBackLike() {
        getPostsFromDB(true)

        manageVisibilityTabFavourites()

        view.showErrorDialog()
    }

    fun manageVisibilityTabFavourites() {
        view.manageVisibilityTabFavourites(showFavouriteTab(newsPosts))
    }

    fun displayNewsFragment() {
        view.showNewsFragment(itemsPositionForUpdatesNews)
        itemsPositionForUpdatesNews.clear()
    }

    fun displayFavouriteFragment() {
        view.showFavouritesFragment(favouriteNews, itemsPositionForUpdatesFavourite)
        itemsPositionForUpdatesFavourite.clear()
    }

    fun displayProfileFragment() {
        view.showProfileFragment()
    }

    private fun showFavouriteTab(list: List<PostItem>): Boolean {
        return !list.none { it.post.likes.userLikes == Constants.IS_LIKED }
    }

    fun onSendPostsList(
        postsList: List<PostItem>,
        postsListType: PostsListType,
        postItem: PostItem?
    ) {
        view.manageVisibilityTabFavourites(showFavouriteTab(postsList))

        favouriteNews =
            postsList.filter { it.post.likes.userLikes == Constants.IS_LIKED } as ArrayList

        when (postsListType) {
            PostsListType.ALL -> {
                if (postItem != null) {
                    // Для обновления предыдущего элемента в избранных
                    itemsPositionForUpdatesFavourite.add(favouriteNews.indexOf(postItem) - 1)
                }

                newsPosts = postsList as ArrayList
            }
            PostsListType.FAVOURITE -> {
                if (postItem != null) {
                    // Для обновления элемента в списке всех новостей
                    itemsPositionForUpdatesNews.add(newsPosts.indexOf(postItem))
                }
            }
        }

        view.showAllNewsIfFavouritesEmpty(favouriteNews)
    }

    fun addNewPost(postItem: PostItem) {
        newsPosts.add(0, postItem)
    }

    fun restoreData() {
        view.restoreData(newsPosts, favouriteNews)
    }
}