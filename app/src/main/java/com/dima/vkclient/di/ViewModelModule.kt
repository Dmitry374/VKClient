package com.dima.vkclient.di

import androidx.lifecycle.ViewModel
import com.dima.vkclient.ui.post.PostViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindViewModel(viewmodel: PostViewModel): ViewModel
}