package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reposts(
    val count: Int,
    @SerializedName("user_reposted")
    val userReposted: Int
) : Parcelable