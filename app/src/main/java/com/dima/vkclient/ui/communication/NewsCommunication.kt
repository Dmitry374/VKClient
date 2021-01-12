package com.dima.vkclient.ui.communication

interface NewsCommunication : NewsFragmentInterface {
    fun onUpdateNewsData(list: List<Int>)
}