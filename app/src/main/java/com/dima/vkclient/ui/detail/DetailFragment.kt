package com.dima.vkclient.ui.detail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.dima.vkclient.App
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.DisplayScreenMetrics
import com.dima.vkclient.common.hideKeyboard
import com.dima.vkclient.data.PostWithCommentsAndDiffResult
import com.dima.vkclient.data.domain.post.PhotoSizeDomain
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.ui.base.MvpFragment
import com.dima.vkclient.ui.communication.FragmentCommunicationInterface
import com.dima.vkclient.ui.detail.adapter.CommentItemDecoration
import com.dima.vkclient.ui.detail.adapter.CommentsAdapter
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel
import com.dima.vkclient.ui.detail.adapter.PostRowListener
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : MvpFragment<DetailView, DetailPresenter>(),
    DetailView {

    companion object {

        const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1

        private const val ARG_POST = "post_item"
        private const val ARG_CONTENT_IMAGE_TRANSITION_NAME = "content_image_transition_name"

        fun newInstance(
            postItem: PostItem
        ): DetailFragment = DetailFragment()
            .apply {
                arguments = bundleOf(
                    ARG_POST to postItem
                )
            }

        fun newInstance(
            postItem: PostItem,
            transitionName: String
        ): DetailFragment =
            DetailFragment().apply {
                arguments =
                    bundleOf(
                        ARG_POST to postItem,
                        ARG_CONTENT_IMAGE_TRANSITION_NAME to transitionName
                    )
            }
    }

    @Inject
    lateinit var displayMetrics: DisplayScreenMetrics

    @Inject
    lateinit var detailPresenter: DetailPresenter

    override fun getPresenter(): DetailPresenter = detailPresenter

    override fun getMvpView(): DetailView = this

    private lateinit var commentsAdapter: CommentsAdapter

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    private var contentImage: PhotoSizeDomain? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).addDetailNewsComponent()
        (requireActivity().application as App).detailNewsComponent?.inject(this)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createPostToolbar = view.findViewById<Toolbar>(R.id.toolbar_posts)
        createPostToolbar.title = resources.getString(R.string.post_detail)
        createPostToolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back, null)

        createPostToolbar.setNavigationOnClickListener {
            commentMessage.hideKeyboard()
            requireActivity().onBackPressed()
        }

        val screenWidth = displayMetrics.getWidth()

        val postItem = requireArguments().getParcelable<PostItem>(ARG_POST) as PostItem

        val transitionName = requireArguments().getString(ARG_CONTENT_IMAGE_TRANSITION_NAME)

        var message: String

        contentImage = getPresenter().getContentImage(postItem, screenWidth)

        val postRowListener: PostRowListener = object : PostRowListener {
            override fun saveImageToGallery() {
                requestWriteExternalStoragePermissionsAndLoadImage()
            }

            override fun shareImage(url: String?) {
                getPresenter().shareImage(contentImage?.url)
            }
        }

        val postAndCommentsUiModels =
            listOf<PostAndCommentUiModel>(PostAndCommentUiModel.PostUiModel(postItem))

        commentsAdapter = CommentsAdapter(
            data = postAndCommentsUiModels,
            transitionNameContentImage = transitionName,
            contentImage = contentImage,
            listener = postRowListener
        )

        val commentItemDecoration = CommentItemDecoration()
        recyclerPostsAndComments.addItemDecoration(commentItemDecoration)

        recyclerPostsAndComments.layoutManager = LinearLayoutManager(activity)
        recyclerPostsAndComments.adapter = commentsAdapter

        getPresenter().loadPostComments(
            postItem = postItem
        )

        if (postItem.post.comments.canPost != Constants.CAN_CREATE_COMMENT) {
            layoutSentComment.visibility = View.GONE
        }

        commentMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                message = commentMessage.text.toString().trim()

                if (message.isNotEmpty()) {
                    buttonCreateComment.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_send,
                            null
                        )
                    )
                } else {
                    buttonCreateComment.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_send_empty,
                            null
                        )
                    )
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        buttonCreateComment.setOnClickListener {
            if (commentMessage.text.isNotEmpty()) {
                getPresenter().createComment(
                    postItem = postItem,
                    message = commentMessage.text.toString()
                )
            }
        }
    }

    override fun updateCommentsData(comments: List<PostAndCommentUiModel>) {

        val postAndComments: MutableList<PostAndCommentUiModel> = mutableListOf()
        postAndComments.addAll(commentsAdapter.data)
        postAndComments.addAll(comments)

        getPresenter().updateData(commentsAdapter.data, postAndComments)
    }

    override fun showNewComment(comment: PostAndCommentUiModel) {

        commentMessage.text.clear()
        commentMessage.hideKeyboard()

        val postAndComments: MutableList<PostAndCommentUiModel> = mutableListOf()
        postAndComments.addAll(commentsAdapter.data)
        postAndComments.add(Constants.AFTER_POST_INDEX, comment)

        commentsAdapter.data = postAndComments

        commentsAdapter.notifyItemInserted(Constants.AFTER_POST_INDEX)

        getPresenter().updateData(commentsAdapter.data, postAndComments)
    }

    override fun showCreateCommentError() {
        Toast.makeText(activity, R.string.error_create_comment, Toast.LENGTH_SHORT).show()
    }

    override fun showComments(postWithCommentsAndDiffResult: PostWithCommentsAndDiffResult) {
        commentsAdapter.data = postWithCommentsAndDiffResult.newList as MutableList
        commentsAdapter.setDiffResult(postWithCommentsAndDiffResult.diffResult)
    }

    override fun showSavedImageMessage() {
        Toast.makeText(activity, R.string.image_saved, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorSavedImageMessage() {
        Toast.makeText(activity, R.string.image_saved_error, Toast.LENGTH_SHORT).show()
    }

    override fun shareImage(imageName: String) {
        fragmentCommunicationInterface?.shareImage(imageName)
    }

    override fun errorShareImage() {
        Toast.makeText(activity, R.string.error_share_image, Toast.LENGTH_SHORT).show()
    }

    private fun requestWriteExternalStoragePermissionsAndLoadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )

            } else {
                getPresenter().loadImage(contentImage?.url)
            }
        } else {
            getPresenter().loadImage(contentImage?.url)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        activity,
                        R.string.disabled_write_external_storage_permission, Toast.LENGTH_SHORT
                    )
                        .show()

                } else {
                    getPresenter().loadImage(contentImage?.url)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (requireActivity().application as App).clearDetailNewsComponent()
    }
}