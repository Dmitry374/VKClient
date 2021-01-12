package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class RepostsDomain(
    val count: Int,
    val userReposted: Int
) : Parcelable