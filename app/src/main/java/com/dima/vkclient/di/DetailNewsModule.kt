package com.dima.vkclient.di

import android.content.Context
import com.dima.vkclient.domain.BitmapSaver
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.detail.DetailPresenter
import dagger.Module
import dagger.Provides

@Module
class DetailNewsModule {

    @DetailNewsScope
    @Provides
    fun provideDetailPresenter(
        context: Context,
        newsInteractor: NewsInteractor,
        bitmapSaver: BitmapSaver
    ): DetailPresenter {
        return DetailPresenter(
            applicationContext = context,
            newsInteractor = newsInteractor,
            bitmapSaver = bitmapSaver
        )
    }
}