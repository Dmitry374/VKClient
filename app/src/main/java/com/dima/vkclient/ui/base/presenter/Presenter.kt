package com.dima.vkclient.ui.base.presenter

interface Presenter<View> {

    fun attachView(view: View)

    fun detachView(isFinishing: Boolean)
}