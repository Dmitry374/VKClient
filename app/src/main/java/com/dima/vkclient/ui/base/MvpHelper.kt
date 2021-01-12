package com.dima.vkclient.ui.base

import com.dima.vkclient.ui.base.presenter.Presenter

class MvpHelper<View, P : Presenter<View>>(
    private val callback: MvpViewCallback<View, P>
) {
    private lateinit var presenter: Presenter<View>

    fun create() {
        presenter = callback.getPresenter()
        presenter.attachView(callback.getMvpView())
    }

    fun destroy(isFinishing: Boolean) {
        presenter.detachView(isFinishing)
    }
}