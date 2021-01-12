package com.dima.vkclient.ui.base

import com.dima.vkclient.ui.base.presenter.Presenter

interface MvpViewCallback<View, P : Presenter<View>> {

    fun getPresenter(): P

    fun getMvpView(): View

}