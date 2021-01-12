package com.dima.vkclient.di

import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.createpost.CreatePostPresenter
import dagger.Module
import dagger.Provides

@Module
class CreatePostModule {

    @CreatePostScope
    @Provides
    fun provideCreatePostPresenter(newsInteractor: NewsInteractor): CreatePostPresenter {
        return CreatePostPresenter(newsInteractor)
    }
}