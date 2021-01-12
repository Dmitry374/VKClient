package com.dima.vkclient.data.domain.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GroupDomain(
    val id: Int,
    val isClosed: Int,
    val name: String,
    val photo100: String,
    val photo200: String,
    val photo50: String,
    val screenName: String?,
    val type: String?
) : Parcelable