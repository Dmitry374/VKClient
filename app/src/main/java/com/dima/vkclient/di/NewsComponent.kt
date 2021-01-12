package com.dima.vkclient.di

import com.dima.vkclient.ui.post.AbstractPostFragment
import dagger.Component

@NewsScope
@Component(dependencies = [AppComponent::class], modules = [NewsModule::class])
interface NewsComponent {

    fun inject(abstractPostFragment: AbstractPostFragment)

}