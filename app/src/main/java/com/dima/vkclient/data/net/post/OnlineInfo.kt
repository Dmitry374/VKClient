package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnlineInfo(
    @SerializedName("app_id")
    val appId: Int,
    @SerializedName("is_mobile")
    val isMobile: Boolean,
    @SerializedName("is_online")
    val isOnline: Boolean,
    @SerializedName("last_seen")
    val lastSeen: Int,
    val visible: Boolean
) : Parcelable