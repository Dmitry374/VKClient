package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(
    val id: Int,
    @SerializedName("is_closed")
    val isClosed: Int,
    val name: String,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_200")
    val photo200: String,
    @SerializedName("photo_50")
    val photo50: String,
    @SerializedName("screen_name")
    val screenName: String?,
    val type: String
) : Parcelable