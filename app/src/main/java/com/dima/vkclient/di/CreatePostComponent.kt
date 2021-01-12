package com.dima.vkclient.di

import com.dima.vkclient.ui.createpost.CreatePostFragment
import dagger.Component

@CreatePostScope
@Component(dependencies = [AppComponent::class], modules = [CreatePostModule::class])
interface CreatePostComponent {

    fun inject(createPostFragment: CreatePostFragment)

}