package com.dima.vkclient.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R
import com.dima.vkclient.ui.post.adapter.PostDiffCallback
import com.dima.vkclient.ui.post.adapter.PostItemDecoration
import com.dima.vkclient.common.*
import com.dima.vkclient.data.NewListAndDiffResult
import com.dima.vkclient.data.domain.post.PostItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostViewModel @Inject constructor(private val resourceProvider: ResourceProvider) :
    ViewModel() {

    var posts = arrayListOf<PostItem>()

    val sectionCallback: PostItemDecoration.SectionCallback by lazy {
        object : PostItemDecoration.SectionCallback {
            override fun isSection(position: Int): Boolean {
                val previousPostDate =
                    posts[if (position - 1 == RecyclerView.NO_POSITION) 0 else position - 1].post.date
                val currentPostDate = posts[position].post.date
                return position == 0 || previousPostDate.getDateOnly() != currentPostDate.getDateOnly()
            }

            override fun isNotLastItemInSection(position: Int): Boolean {
                val currentPostDate = posts[position].post.date
                val nextPosition =
                    if (position + 1 > posts.size - 1) RecyclerView.NO_POSITION else position + 1
                val nextPostDate = if (nextPosition == RecyclerView.NO_POSITION) {
                    null
                } else {
                    posts[nextPosition].post.date
                }
                return currentPostDate.getDateOnly() == nextPostDate?.getDateOnly()
            }

            override fun getSectionHeaderName(position: Int): String {
                return when {
                    posts[position].post.date.isToday() -> {
                        resourceProvider.getString(R.string.today)
                    }
                    posts[position].post.date.isYesterday() -> {
                        resourceProvider.getString(R.string.yesterday)
                    }
                    else -> {
                        posts[position].post.date.getDateTextForHeader()
                    }
                }
            }
        }
    }

    private val postsCompositeDisposable by lazy { CompositeDisposable() }

    private val _newListAndDiffResult by lazy { MutableLiveData<NewListAndDiffResult>() }
    val newListAndDiffResult: LiveData<NewListAndDiffResult>
        get() = _newListAndDiffResult

    fun updateData(oldList: List<PostItem>, newList: List<PostItem>) {
        postsCompositeDisposable.add(
            Observable.fromCallable {
                val postDiffCallback = PostDiffCallback(oldList, newList)
                DiffUtil.calculateDiff(postDiffCallback, false)
            }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { diffResult ->
                    val newListAndDiffResult = NewListAndDiffResult(newList, diffResult)
                    _newListAndDiffResult.value = newListAndDiffResult
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        postsCompositeDisposable.clear()
    }
}