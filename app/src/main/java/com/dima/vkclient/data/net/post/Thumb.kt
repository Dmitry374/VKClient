package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumb(
    @SerializedName("access_key")
    val accessKey: String,
    @SerializedName("album_id")
    val albumId: Int,
    val date: Int,
    @SerializedName("has_tags")
    val hasTags: Boolean,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val sizes: List<Size>,
    val text: String,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable