package com.dima.vkclient.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dima.vkclient.App
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.ui.base.MvpFragment
import com.dima.vkclient.ui.communication.FragmentCommunicationInterface
import com.dima.vkclient.ui.communication.UserProfileCommunication
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostItemDecoration
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel
import com.dima.vkclient.ui.profile.adapter.UserProfileAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : MvpFragment<ProfileView, ProfilePresenter>(), ProfileView,
    UserProfileCommunication {

    @Inject
    lateinit var displayMetrics: DisplayScreenMetrics

    @Inject
    lateinit var profilePresenter: ProfilePresenter

    override fun getPresenter(): ProfilePresenter = profilePresenter

    override fun getMvpView(): ProfileView = this

    private lateinit var userProfileAdapter: UserProfileAdapter

    private val profileViewModel: ProfileViewModel by viewModels()

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).profileComponent?.inject(this)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_posts)
        toolbar.title = resources.getString(R.string.profile)

        initSwipeToRefresh()

        val itemDecoration = ProfileAndPostItemDecoration()
        recyclerProfileAndPosts.addItemDecoration(itemDecoration)

        userProfileAdapter = UserProfileAdapter(
            data = listOf(),
            displayScreenMetrics = displayMetrics
        ) { post, contentImage, transitionName ->
            fragmentCommunicationInterface?.onOpenPostDetail(
                postItem = post,
                contentImageView = contentImage,
                transitionName = transitionName
            )
        }

        recyclerProfileAndPosts.layoutManager = LinearLayoutManager(activity)
        recyclerProfileAndPosts.adapter = userProfileAdapter

        profileViewModel.rowTypeUserProfilesAndDiffResult.observe(
            viewLifecycleOwner,
            Observer { rowTypeUserProfilesAndDiffResult ->
                userProfileAdapter.data = rowTypeUserProfilesAndDiffResult.newList
                userProfileAdapter.setDiffResult(rowTypeUserProfilesAndDiffResult.diffResult)
            })

        createPost.setOnClickListener {
            fragmentCommunicationInterface?.onCreatePost()
        }
    }

    private fun initSwipeToRefresh() {
        swipeRefreshLayoutProfile.setOnRefreshListener {

            loadUserProfileDataAndWallPosts()

            swipeRefreshLayoutProfile.isRefreshing = false
        }

        swipeRefreshLayoutProfile.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_purple,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
    }

    override fun loadUserProfileDataAndWallPosts() {
        getPresenter().loadUserProfileData()
    }

    override fun displayNewPost(profileAndPostsUiModel: ProfileAndPostsUiModel) {

        val profileAndPosts: MutableList<ProfileAndPostsUiModel> = mutableListOf()
        profileAndPosts.addAll(userProfileAdapter.data)
        profileAndPosts.add(Constants.AFTER_PROFILE_INDEX, profileAndPostsUiModel)

        profileViewModel.updateData(userProfileAdapter.data, profileAndPosts)
    }

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
        recyclerProfileAndPosts.visibility = View.GONE
        createPost.visibility = View.GONE
        profileShimmerViewContainer.visibility = View.VISIBLE
        profileShimmerViewContainer.startShimmer()
    }

    override fun stopShimmerAnimation() {
        recyclerProfileAndPosts.visibility = View.VISIBLE
        createPost.visibility = View.VISIBLE
        profileShimmerViewContainer.visibility = View.GONE
        profileShimmerViewContainer.stopShimmer()
    }

    override fun displayProfile(profileUiModel: ProfileAndPostsUiModel) {
        profileViewModel.updateData(
            userProfileAdapter.data,
            listOf(profileUiModel)
        )
    }

    override fun displayWallPosts(posts: List<ProfileAndPostsUiModel>) {

        val profileAndPosts: MutableList<ProfileAndPostsUiModel> = mutableListOf()
        profileAndPosts.addAll(userProfileAdapter.data)
        profileAndPosts.addAll(posts)

        profileViewModel.updateData(userProfileAdapter.data, profileAndPosts)
    }
}