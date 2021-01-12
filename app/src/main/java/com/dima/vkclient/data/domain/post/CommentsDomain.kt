package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CommentsDomain(
    val canPost: Int,
    var count: Int,
    val groupsCanPost: Boolean
) : Parcelable