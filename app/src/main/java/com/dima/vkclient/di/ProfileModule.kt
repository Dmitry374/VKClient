package com.dima.vkclient.di

import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.profile.ProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {

    @ProfileScope
    @Provides
    fun provideProfilePresenter(newsInteractor: NewsInteractor): ProfilePresenter {
        return ProfilePresenter(newsInteractor)
    }
}