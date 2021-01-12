package com.dima.vkclient.di

import com.dima.vkclient.ui.profile.ProfileFragment
import dagger.Component

@ProfileScope
@Component(dependencies = [AppComponent::class], modules = [ProfileModule::class])
interface ProfileComponent {

    fun inject(profileFragment: ProfileFragment)

}