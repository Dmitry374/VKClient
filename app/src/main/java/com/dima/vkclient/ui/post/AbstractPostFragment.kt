package com.dima.vkclient.ui.post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.dima.vkclient.App
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.ui.base.MvpFragment
import com.dima.vkclient.ui.communication.FragmentCommunicationInterface
import com.dima.vkclient.ui.communication.NewsFragmentInterface
import com.dima.vkclient.ui.post.adapter.*
import kotlinx.android.synthetic.main.fragment_posts_list.*
import javax.inject.Inject

abstract class AbstractPostFragment : MvpFragment<PostView, PostPresenter>(), PostView,
    NewsFragmentInterface {

    protected lateinit var postsAdapter: PostsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val postViewModel: PostViewModel by viewModels {
        viewModelFactory
    }

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    @Inject
    lateinit var displayMetrics: DisplayScreenMetrics

    @Inject
    lateinit var postPresenter: PostPresenter

    override fun getPresenter(): PostPresenter = postPresenter

    override fun getMvpView(): PostView = this

    abstract fun getType(): PostsListType

    override fun showErrorLayout() {
        serverErrorLayout.visibility = View.VISIBLE
    }

    override fun hideErrorLayout() {
        serverErrorLayout.visibility = View.GONE
    }

    override fun showErrorDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.error_loading_title)
            .setMessage(R.string.error_loading_message)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()
    }

    override fun startShimmerAnimation() {
        recyclerPosts.visibility = View.GONE
        shimmerViewContainer.visibility = View.VISIBLE
        shimmerViewContainer.startShimmer()
    }

    override fun stopShimmerAnimation() {
        recyclerPosts.visibility = View.VISIBLE
        shimmerViewContainer.visibility = View.GONE
        shimmerViewContainer.stopShimmer()
    }

    override fun onRestoreAllData(list: List<PostItem>) {
        postViewModel.posts = list as ArrayList
        postsAdapter.postItems = list
    }

    override fun onUpdatePostsData(list: List<PostItem>) {
        postViewModel.posts = list as ArrayList
        postViewModel.updateData(postsAdapter.postItems, list)
    }

    override fun onErrorHidePost(postItem: PostItem, position: Int) {
        showErrorDialog()

        postViewModel.posts.add(position, postItem)
        postsAdapter.update(listOf(position, position + 1))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).newsComponent?.inject(this)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_posts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        swipeRefreshLayoutPosts.setOnRefreshListener {

            fragmentCommunicationInterface?.loadNews()

            swipeRefreshLayoutPosts.isRefreshing = false
        }

        swipeRefreshLayoutPosts.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_purple,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
    }

    protected fun initRecyclerView() {
        val postItemDecoration = PostItemDecoration(postViewModel.sectionCallback)
        recyclerPosts.addItemDecoration(postItemDecoration)

        val postListener: PostListener = object : PostListener {

            override fun addLike(postItem: PostItem) {
                postItem.post.likes.userLikes += 1
                postItem.post.likes.count += 1
                fragmentCommunicationInterface?.addLike(postItem)
            }

            override fun deleteLike(postItem: PostItem) {
                postItem.post.likes.userLikes -= 1
                postItem.post.likes.count -= 1
                fragmentCommunicationInterface?.deleteLike(postItem)
            }

            override fun onHidePost(postItem: PostItem, position: Int) {
                postViewModel.posts.removeAt(position)
                getPresenter().hidePost(
                    postItem = postItem,
                    type = Constants.WALL_TYPE,
                    position = position
                )
            }

            override fun removePost(position: Int) {
                postViewModel.posts.removeAt(position)
            }

            override fun onSendPostsListToActivity(
                postsListType: PostsListType,
                postItem: PostItem
            ) {
                fragmentCommunicationInterface?.onSendPostsList(
                    postViewModel.posts,
                    postsListType,
                    postItem
                )
            }
        }

        postsAdapter = PostsAdapter(
            postItems = postViewModel.posts,
            sectionCallback = postViewModel.sectionCallback,
            postsListType = getType(),
            postListener = postListener,
            displayScreenMetrics = displayMetrics
        ) { post, contentImage, transitionName ->
            fragmentCommunicationInterface?.onOpenPostDetail(
                postItem = post,
                contentImageView = contentImage,
                transitionName = transitionName
            )
        }

        recyclerPosts.layoutManager = LinearLayoutManager(activity)
        recyclerPosts.adapter = postsAdapter

        val callback =
            PostItemTouchHelperCallback(
                postsAdapter as PostItemTouchHelperAdapter
            )
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerPosts)

        postViewModel.newListAndDiffResult.observe(
            viewLifecycleOwner,
            Observer { newListAndDiffResult ->
                postsAdapter.postItems = newListAndDiffResult.newList as ArrayList
                postsAdapter.setDiffResult(newListAndDiffResult.diffResult)
            })

    }
}