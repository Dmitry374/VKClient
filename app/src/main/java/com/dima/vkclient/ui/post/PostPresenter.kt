package com.dima.vkclient.ui.post

import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.base.presenter.RxPresenter

class PostPresenter(
    private val newsInteractor: NewsInteractor
) : RxPresenter<PostView>(PostView::class.java) {

    fun hidePost(postItem: PostItem, type: String, position: Int) {

        newsInteractor.hidePost(postItem, type)
            .subscribe({
            }, {
                view.onErrorHidePost(postItem, position)
            })
            .disposeOnFinish()
    }

}