package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comments(
    @SerializedName("can_post")
    val canPost: Int,
    val count: Int,
    @SerializedName("groups_can_post")
    val groupsCanPost: Boolean
) : Parcelable