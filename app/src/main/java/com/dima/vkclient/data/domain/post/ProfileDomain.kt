package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileDomain(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val online: Int,
    val photo100: String,
    val photo50: String,
    val screenName: String?,
    val sex: Int,
    val onlineInfo: OnlineInfoDomain
) : Parcelable