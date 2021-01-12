package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OnlineInfoDomain(
    val appId: Int,
    val isMobile: Boolean,
    val isOnline: Boolean,
    val lastSeen: Int,
    val visible: Boolean
) : Parcelable