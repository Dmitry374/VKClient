package com.dima.vkclient.di

import com.dima.vkclient.ui.detail.DetailFragment
import dagger.Component

@DetailNewsScope
@Component(dependencies = [AppComponent::class], modules = [DetailNewsModule::class])
interface DetailNewsComponent {

    fun inject(detailFragment: DetailFragment)

}