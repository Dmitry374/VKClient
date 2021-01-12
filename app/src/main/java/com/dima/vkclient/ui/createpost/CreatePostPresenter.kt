package com.dima.vkclient.ui.createpost

import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.base.presenter.RxPresenter

class CreatePostPresenter(private val newsInteractor: NewsInteractor) :
    RxPresenter<CreatePostView>(CreatePostView::class.java) {

    fun createPost(message: String) {
        newsInteractor.createPost(message)
            .subscribe({ postsItemAndProfileUiModel ->
                view.displayNewPost(postsItemAndProfileUiModel)
            }, {
                view.showErrorDialog()
            })
            .disposeOnFinish()
    }
}