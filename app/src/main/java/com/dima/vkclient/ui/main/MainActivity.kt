package com.dima.vkclient.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dima.vkclient.App
import com.dima.vkclient.BuildConfig
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.data.PostsItemAndProfileUiModel
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.network.TokenHelper
import com.dima.vkclient.ui.base.MvpActivity
import com.dima.vkclient.ui.communication.*
import com.dima.vkclient.ui.createpost.CreatePostFragment
import com.dima.vkclient.ui.detail.DetailFragment
import com.dima.vkclient.ui.post.FavouriteFragment
import com.dima.vkclient.ui.post.NewsFragment
import com.dima.vkclient.ui.post.adapter.PostsListType
import com.dima.vkclient.ui.profile.ProfileFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView,
    FragmentCommunicationInterface {

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        private const val KEY_TAB_POSITION = "tab_position"
        private const val KEY_TAB_NEWS_POSITION = 0
        private const val KEY_TAB_FAVOURITE_POSITION = 1
        private const val KEY_TAB_PROFILE_POSITION = 2

        private const val NEWS_FRAGMENT_TAG = "news_fragment"
        private const val FAVOURITE_FRAGMENT_TAG = "favourite_fragment"
        private const val PROFILE_FRAGMENT_TAG = "profile_fragment"

        private const val IS_SHOW_BOTTOM_MENU = "is_show_bottom_menu"
    }

    private val fragmentManager = supportFragmentManager

    private var newsCommunication: NewsCommunication? = null
    private var favouriteCommunication: FavouriteCommunication? = null
    private var userProfileCommunication: UserProfileCommunication? = null

    private var newsFragment = NewsFragment()
    private var favouriteFragment = FavouriteFragment()
    private var profileFragment = ProfileFragment()
    private var createPostFragment = CreatePostFragment()
    private var activeFragment: Fragment = Fragment()

    private var isShowBottomMenu = true

    private var currentTab = KEY_TAB_NEWS_POSITION

    @Inject
    lateinit var tokenHelper: TokenHelper

    @Inject
    lateinit var displayMetrics: DisplayScreenMetrics

    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun getPresenter(): MainPresenter = mainPresenter

    override fun getMvpView(): MainView = this

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        (applicationContext as App).addNewsComponent()
        (applicationContext as App).addProfileComponent()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveDisplayMetrics()

        if (savedInstanceState == null) {
            VK.login(this, arrayListOf(VKScope.WALL, VKScope.FRIENDS))
        }

        isShowBottomMenu = savedInstanceState?.getBoolean(IS_SHOW_BOTTOM_MENU) ?: true

        if (isShowBottomMenu) {
            showBottomNav()
        } else {
            hideBottomNav()
        }

        currentTab = savedInstanceState?.getInt(KEY_TAB_POSITION) ?: KEY_TAB_NEWS_POSITION

        if (savedInstanceState == null) {

            fragmentManager.beginTransaction().apply {
                add(R.id.nav_host_container, profileFragment, PROFILE_FRAGMENT_TAG).hide(profileFragment)
                add(R.id.nav_host_container, favouriteFragment, FAVOURITE_FRAGMENT_TAG).hide(favouriteFragment)
                add(R.id.nav_host_container, newsFragment, NEWS_FRAGMENT_TAG)
            }.commit()
        } else {

            getPresenter().manageVisibilityTabFavourites()

            newsFragment = fragmentManager.findFragmentByTag(NEWS_FRAGMENT_TAG) as NewsFragment
            favouriteFragment = fragmentManager.findFragmentByTag(FAVOURITE_FRAGMENT_TAG) as FavouriteFragment
            profileFragment = fragmentManager.findFragmentByTag(PROFILE_FRAGMENT_TAG) as ProfileFragment
        }

        initCommunicationInterfacesAndActiveFragment()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.news -> {

                    getPresenter().displayNewsFragment()

                    true
                }
                R.id.favourite -> {

                    getPresenter().displayFavouriteFragment()

                    true
                }
                R.id.profile -> {

                    getPresenter().displayProfileFragment()

                    true
                }
                else -> false
            }
        }

        fragmentManager.addOnBackStackChangedListener(backStackListener)

        registerNetworkCallback()
    }

    override fun loadNews() {
        getPresenter().loadNews(Constants.FILTER_POST, true)
    }

    private fun saveDisplayMetrics() {

        val metrics = resources.displayMetrics

        val width = metrics.widthPixels
        val height = metrics.heightPixels

        displayMetrics.saveDisplayMetrics(width, height)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                tokenHelper.token = token.accessToken
                getPresenter().loadNews(Constants.FILTER_POST, false)
                userProfileCommunication?.loadUserProfileDataAndWallPosts()
            }

            override fun onLoginFailed(errorCode: Int) {
                Log.e(TAG, "Login Failed")
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            getPresenter().getPostsFromDB(false)
            userProfileCommunication?.loadUserProfileDataAndWallPosts()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    Toast.makeText(this@MainActivity, R.string.error_internet_connection, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun displayNewPost(postsItemAndProfileUiModel: PostsItemAndProfileUiModel) {

        fragmentManager.popBackStackImmediate()

        userProfileCommunication?.displayNewPost(postsItemAndProfileUiModel.profileAndPostsUiModel)

        getPresenter().addNewPost(postsItemAndProfileUiModel.postItem)
    }

    override fun onSendPostsList(postsList: List<PostItem>, postsListType: PostsListType, postItem: PostItem?) {
        getPresenter().onSendPostsList(postsList, postsListType, postItem)
    }

    override fun showAllNewsIfFavouritesEmpty(favouriteNews: List<PostItem>) {
        if (favouriteNews.isEmpty() && currentTab == KEY_TAB_FAVOURITE_POSITION) {
            getPresenter().displayNewsFragment()
        }
    }

    override fun onOpenPostDetail(
        postItem: PostItem,
        contentImageView: ImageView?,
        transitionName: String?
    ) {
        if (contentImageView != null && transitionName != null) {
            fragmentManager.beginTransaction()
                .addSharedElement(contentImageView, transitionName)
                .replace(R.id.nav_host_container, DetailFragment.newInstance(postItem, transitionName))
                .addToBackStack(null)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_container, DetailFragment.newInstance(postItem))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreatePost() {
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_container, createPostFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun addLike(postItem: PostItem) {
        getPresenter().addLike(postItem)
    }

    override fun deleteLike(postItem: PostItem) {
        getPresenter().deleteLike(postItem)
    }

    override fun shareImage(imageName: String) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            val file = File(cacheDir, imageName)
            val uri = FileProvider.getUriForFile(this@MainActivity, BuildConfig.APPLICATION_ID, file)

            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.send_to)))
    }

    override fun showErrorDialog() {
        when (currentTab) {
            KEY_TAB_NEWS_POSITION -> newsCommunication?.showErrorDialog()
            KEY_TAB_FAVOURITE_POSITION -> favouriteCommunication?.showErrorDialog()
            else -> throw IllegalArgumentException("No such tab")
        }
    }

    override fun manageVisibilityTabFavourites(showFavourites: Boolean) {
        bottomNavigation.menu.findItem(R.id.favourite).isVisible = showFavourites
    }

    override fun showNewsFragment(updatePositions: List<Int>) {
        bottomNavigation.menu.findItem(R.id.news).isChecked = true

        currentTab = KEY_TAB_NEWS_POSITION

        newsCommunication?.onUpdateNewsData(updatePositions)

        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).hide(activeFragment)
            .show(newsFragment)
            .commit()
        activeFragment = newsFragment
    }

    override fun showFavouritesFragment(favouriteNews: List<PostItem>, updatePositions: List<Int>) {

        var enter = 0
        var exit = 0

        when (currentTab) {
            KEY_TAB_NEWS_POSITION -> {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
            }
            KEY_TAB_PROFILE_POSITION -> {
                enter = R.anim.slide_in_left
                exit = R.anim.slide_out_right
            }
        }

        currentTab = KEY_TAB_FAVOURITE_POSITION

        favouriteCommunication?.onUpdateFavouriteNewsData(
            favouriteNews,
            updatePositions
        )

        fragmentManager.beginTransaction()
            .setCustomAnimations(enter, exit).hide(activeFragment)
            .show(favouriteFragment)
            .commit()
        activeFragment = favouriteFragment
    }

    override fun showProfileFragment() {
        currentTab = KEY_TAB_PROFILE_POSITION

        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).hide(activeFragment)
            .show(profileFragment)
            .commit()
        activeFragment = profileFragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_TAB_POSITION, currentTab)
        outState.putBoolean(IS_SHOW_BOTTOM_MENU, isShowBottomMenu)
    }

    private val backStackListener = FragmentManager.OnBackStackChangedListener {
        isShowBottomMenu = fragmentManager.findFragmentById(R.id.nav_host_container) is TabFragmentInterface

        if (isShowBottomMenu) {
            showBottomNav()
            getPresenter().restoreData()
        } else {
            hideBottomNav()
        }
    }

    override fun setFirstDataFromDB(newsPosts: List<PostItem>) {
        val fragment = fragmentManager.findFragmentById(R.id.nav_host_container)
        if (fragment is NewsFragmentInterface) {
            (fragment as NewsFragmentInterface).onUpdatePostsData(newsPosts)
        }
    }

    override fun restoreData(newsPosts: List<PostItem>, favouriteNews: List<PostItem>) {
        newsCommunication?.onRestoreAllData(newsPosts)
        favouriteCommunication?.onRestoreAllData(favouriteNews)
    }

    override fun refreshAllData(newsPosts: List<PostItem>, favouriteNews: List<PostItem>) {
        if (favouriteNews.isEmpty() && currentTab == KEY_TAB_FAVOURITE_POSITION) {
            getPresenter().displayNewsFragment()
        }
        newsCommunication?.onUpdatePostsData(newsPosts)
        favouriteCommunication?.onUpdatePostsData(favouriteNews)
    }

    private fun showBottomNav() {
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        bottomNavigation.visibility = View.GONE
    }

    override fun showErrorLayout() {
        newsCommunication?.stopShimmerAnimation()
        favouriteCommunication?.stopShimmerAnimation()

        newsCommunication?.showErrorLayout()
        favouriteCommunication?.showErrorLayout()
        userProfileCommunication?.showErrorLayout()
    }

    override fun stopShimmerAnimation() {
        newsCommunication?.stopShimmerAnimation()
        favouriteCommunication?.stopShimmerAnimation()
    }

    override fun startShimmerAnimation() {
        newsCommunication?.hideErrorLayout()
        favouriteCommunication?.hideErrorLayout()
        userProfileCommunication?.hideErrorLayout()

        newsCommunication?.startShimmerAnimation()
        favouriteCommunication?.startShimmerAnimation()
    }

    private fun initCommunicationInterfacesAndActiveFragment() {
        newsCommunication = newsFragment
        favouriteCommunication = favouriteFragment
        userProfileCommunication = profileFragment

        activeFragment = when (currentTab) {
            KEY_TAB_NEWS_POSITION -> newsFragment
            KEY_TAB_FAVOURITE_POSITION -> favouriteFragment
            KEY_TAB_PROFILE_POSITION -> profileFragment
            else -> throw IllegalArgumentException("No such tab")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (applicationContext as App).clearNewsComponent()
        (applicationContext as App).clearProfileComponent()
    }
}