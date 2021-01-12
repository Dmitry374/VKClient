package com.dima.vkclient.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.common.ResourceProvider
import com.dima.vkclient.domain.BitmapSaver
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.repository.NewsRepository
import com.dima.vkclient.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun context(): Context

    fun displayScreenMetrics(): DisplayScreenMetrics

    fun resourceProvider(): ResourceProvider

    fun viewModelFactory(): ViewModelProvider.Factory

    fun bitmapSaver(): BitmapSaver

    fun newsInteractor(): NewsInteractor

    fun newsRepository(): NewsRepository

}