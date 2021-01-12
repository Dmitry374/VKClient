package com.dima.vkclient.ui.base.presenter

import com.dima.vkclient.utils.ReflectionUtils

abstract class BasePresenter<View> protected constructor(
    viewClass: Class<View>
) : Presenter<View> {

    private val stubView: View = ReflectionUtils.createStub(viewClass)
    private var realView: View? = null

    val view: View
        get() = realView ?: stubView

    override fun attachView(view: View) {
        this.realView = view
    }

    override fun detachView(isFinishing: Boolean) {
        this.realView = null
    }

    fun hasView(): Boolean {
        return view != null
    }
}