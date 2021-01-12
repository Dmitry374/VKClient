package com.dima.vkclient.ui.createpost

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.dima.vkclient.App
import com.dima.vkclient.R
import com.dima.vkclient.common.hideKeyboard
import com.dima.vkclient.data.PostsItemAndProfileUiModel
import com.dima.vkclient.ui.base.MvpFragment
import com.dima.vkclient.ui.communication.FragmentCommunicationInterface
import kotlinx.android.synthetic.main.fragment_create_post.*
import javax.inject.Inject

class CreatePostFragment : MvpFragment<CreatePostView, CreatePostPresenter>(), CreatePostView {

    @Inject
    lateinit var createPostPresenter: CreatePostPresenter

    override fun getPresenter(): CreatePostPresenter = createPostPresenter

    override fun getMvpView(): CreatePostView = this

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    private var message = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).addCreatePostComponent()
        (requireActivity().application as App).createPostComponent?.inject(this)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createPostToolbar = view.findViewById<Toolbar>(R.id.toolbar_posts)
        createPostToolbar.title = resources.getString(R.string.create_post)
        createPostToolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null)

        createPostToolbar.setNavigationOnClickListener {
            hideSoftKeyBoardAndClearText()
            requireActivity().onBackPressed()
        }

        createPostToolbar.inflateMenu(R.menu.create_post_menu)
        val createPostMenu = createPostToolbar.menu

        createPostToolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.create_post -> {
                    getPresenter().createPost(message)
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true
                }
            }
        })

        postTextMessage.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                message = postTextMessage.text.toString().trim()

                if (message.isNotEmpty()) {
                    createPostMenu.findItem(R.id.create_post)?.isVisible = true
                    createPostMenu.findItem(R.id.create_post_inactive)?.isVisible = false
                } else {
                    createPostMenu.findItem(R.id.create_post)?.isVisible = false
                    createPostMenu.findItem(R.id.create_post_inactive)?.isVisible = true
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun hideSoftKeyBoardAndClearText() {
        postTextMessage.hideKeyboard()
        postTextMessage.text.clear()
    }

    override fun showErrorDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.error_loading_title)
            .setMessage(R.string.error_loading_message)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()
    }

    override fun displayNewPost(postsItemAndProfileUiModel: PostsItemAndProfileUiModel) {
        postTextMessage.text.clear()
        fragmentCommunicationInterface?.displayNewPost(postsItemAndProfileUiModel)
    }

    override fun onDestroy() {
        super.onDestroy()

        (requireActivity().application as App).clearCreatePostComponent()
    }
}