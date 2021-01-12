package com.dima.vkclient.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: NewsViewModelFactory
    ): ViewModelProvider.Factory
}