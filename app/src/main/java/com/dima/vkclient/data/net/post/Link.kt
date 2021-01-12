package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Link(
    val caption: String,
    val description: String,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val photo: PhotoX,
    val target: String,
    val title: String,
    val url: String
) : Parcelable