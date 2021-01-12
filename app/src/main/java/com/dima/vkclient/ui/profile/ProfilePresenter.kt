package com.dima.vkclient.ui.profile

import com.dima.vkclient.common.Constants
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.base.presenter.RxPresenter

class ProfilePresenter(
    private val newsInteractor: NewsInteractor
) : RxPresenter<ProfileView>(ProfileView::class.java) {

    fun loadUserProfileData() {
        view.startShimmerAnimation()
        newsInteractor.loadUserProfileData(
            fields = Constants.USER_PROFILE_FIELDS
        )
            .subscribe({ profileUiModel ->
                view.stopShimmerAnimation()
                view.displayProfile(profileUiModel)
                getWallNews()
            }, {
                view.stopShimmerAnimation()
                view.showErrorDialog()
            })
            .disposeOnFinish()
    }

    private fun getWallNews() {
        newsInteractor.getWallNews()
            .subscribe({ posts ->
                view.displayWallPosts(posts)
            }, {

            })
            .disposeOnFinish()
    }
}