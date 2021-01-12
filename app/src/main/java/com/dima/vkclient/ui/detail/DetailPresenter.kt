package com.dima.vkclient.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dima.vkclient.common.Constants
import com.dima.vkclient.data.PostWithCommentsAndDiffResult
import com.dima.vkclient.data.domain.post.PhotoSizeDomain
import com.dima.vkclient.data.domain.post.PostItem
import com.dima.vkclient.domain.BitmapSaver
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.ui.base.presenter.RxPresenter
import com.dima.vkclient.ui.detail.adapter.CommentDiffCallback
import com.dima.vkclient.ui.detail.adapter.PostAndCommentUiModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailPresenter(
    private val applicationContext: Context,
    private val newsInteractor: NewsInteractor,
    private val bitmapSaver: BitmapSaver
) : RxPresenter<DetailView>(DetailView::class.java) {

    fun loadPostComments(postItem: PostItem) {
        newsInteractor.loadPostComments(postItem)
            .subscribe({
                getCommentsFromDb(postItem.post.id)
            }, {
                getCommentsFromDb(postItem.post.id)
            })
            .disposeOnFinish()
    }

    private fun getCommentsFromDb(postId: Int) {
        newsInteractor.getCommentsLocal(postId)
            .subscribe({ commentsUiModel ->
                view.updateCommentsData(commentsUiModel)
            }, {

            })
            .disposeOnFinish()
    }

    fun createComment(
        postItem: PostItem,
        message: String
    ) {
        newsInteractor.createComment(postItem, message)
            .subscribe({ commentUiModel ->
                view.showNewComment(commentUiModel)
            }, {
                view.showCreateCommentError()
            })
            .disposeOnFinish()
    }

    fun updateData(oldList: List<PostAndCommentUiModel>, newList: List<PostAndCommentUiModel>) {
        Observable.fromCallable {
            val commentDiffCallback = CommentDiffCallback(oldList, newList)
            DiffUtil.calculateDiff(commentDiffCallback, false)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { diffResult ->
                val postWithCommentsAndDiffResult =
                    PostWithCommentsAndDiffResult(newList, diffResult)
                view.showComments(postWithCommentsAndDiffResult)
            }
            .disposeOnFinish()
    }

    fun getContentImage(postItem: PostItem, screenWidth: Int): PhotoSizeDomain? {
        var photoWithSize: PhotoSizeDomain? = null

        postItem.attachments?.find { it.attachment.type == Constants.ATTACHMENT_TYPE_PHOTO }
            ?.let { attachment ->

                attachment.photos?.forEach { size ->
                    if (size.width <= screenWidth) {
                        photoWithSize = size
                    }
                }
            }

        return photoWithSize
    }

    fun loadImage(url: String?) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    saveImage(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun saveImage(bitmap: Bitmap) {
        bitmapSaver.saveImage(bitmap)
            .subscribe({
                view.showSavedImageMessage()
            }, {
                view.showErrorSavedImageMessage()
            })
            .disposeOnFinish()
    }

    fun shareImage(url: String?) {
        Glide.with(applicationContext)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    saveImageInCache(bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun saveImageInCache(bitmap: Bitmap) {
        bitmapSaver.saveImageInCache(bitmap)
            .subscribe { imageName ->
                if (imageName != null) {
                    view.shareImage(imageName)
                } else {
                    view.errorShareImage()
                }
            }
            .disposeOnFinish()
    }

}