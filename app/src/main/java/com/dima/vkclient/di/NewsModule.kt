package com.dima.vkclient.di

import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.post.PostPresenter
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @NewsScope
    @Provides
    fun providePostPresenter(newsInteractor: NewsInteractor): PostPresenter {
        return PostPresenter(newsInteractor)
    }
}