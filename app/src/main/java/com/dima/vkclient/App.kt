package com.dima.vkclient

import android.app.Application
import android.util.Log
import com.dima.vkclient.di.*
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class App : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
    }

    lateinit var appComponent: AppComponent
    var newsComponent: NewsComponent? = null
    var detailNewsComponent: DetailNewsComponent? = null
    var profileComponent: ProfileComponent? = null
    var createPostComponent: CreatePostComponent? = null

    override fun onCreate() {
        super.onCreate()

        VK.addTokenExpiredHandler(tokenTracker)

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            Log.e(TAG, "Token expired")
        }
    }

    fun addNewsComponent() {
        newsComponent = DaggerNewsComponent.builder().appComponent(appComponent).build()
    }

    fun clearNewsComponent() {
        newsComponent = null
    }

    fun addDetailNewsComponent() {
        detailNewsComponent = DaggerDetailNewsComponent.builder().appComponent(appComponent).build()
    }

    fun clearDetailNewsComponent() {
        detailNewsComponent = null
    }

    fun addProfileComponent() {
        profileComponent = DaggerProfileComponent.builder().appComponent(appComponent).build()
    }

    fun clearProfileComponent() {
        profileComponent = null
    }

    fun addCreatePostComponent() {
        createPostComponent = DaggerCreatePostComponent.builder().appComponent(appComponent).build()
    }

    fun clearCreatePostComponent() {
        createPostComponent = null
    }
}