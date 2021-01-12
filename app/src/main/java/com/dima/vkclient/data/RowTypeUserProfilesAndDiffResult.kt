package com.dima.vkclient.data

import androidx.recyclerview.widget.DiffUtil
import com.dima.vkclient.ui.profile.adapter.ProfileAndPostsUiModel

class RowTypeUserProfilesAndDiffResult(
    val newList: List<ProfileAndPostsUiModel>,
    val diffResult: DiffUtil.DiffResult
)