package com.dima.vkclient.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.dima.vkclient.data.RowTypeUserProfilesAndDiffResult
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel
import com.dima.vkclient.ui.profile.adapter.UserProfileDiffCallback
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _rowTypeUserProfilesAndDiffResult by lazy { MutableLiveData<RowTypeUserProfilesAndDiffResult>() }
    val rowTypeUserProfilesAndDiffResult: LiveData<RowTypeUserProfilesAndDiffResult>
        get() = _rowTypeUserProfilesAndDiffResult

    fun updateData(oldList: List<ProfileAndPostsUiModel>, newList: List<ProfileAndPostsUiModel>) {
        compositeDisposable.add(
            Observable.fromCallable {
                val userProfileDiffCallback =
                    UserProfileDiffCallback(
                        oldList,
                        newList
                    )
                DiffUtil.calculateDiff(userProfileDiffCallback, false)
            }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { diffResult ->

                    val rowTypeUserProfilesAndDiffResult =
                        RowTypeUserProfilesAndDiffResult(newList, diffResult)
                    _rowTypeUserProfilesAndDiffResult.value = rowTypeUserProfilesAndDiffResult
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}