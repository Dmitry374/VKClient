package com.dima.vkclient.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dima.vkclient.common.ResourceProvider

class PostViewModelFactory(private val resourceProvider: ResourceProvider) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(
                resourceProvider
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}