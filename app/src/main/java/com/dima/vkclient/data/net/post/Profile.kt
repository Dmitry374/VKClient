package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    val deactivated: String,
    @SerializedName("first_name")
    val firstName: String,
    val id: Int,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("last_name")
    val lastName: String,
    val online: Int,
    @SerializedName("online_app")
    val onlineApp: Int,
    @SerializedName("online_info")
    val onlineInfo: OnlineInfo,
    @SerializedName("online_mobile")
    val onlineMobile: Int,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_50")
    val photo50: String,
    @SerializedName("screen_name")
    val screenName: String?,
    val sex: Int
) : Parcelable