package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PodcastInfo(
    val cover: Cover,
    val description: String,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val plays: Int
) : Parcelable