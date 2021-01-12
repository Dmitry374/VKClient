package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoXXX(
    @SerializedName("album_id")
    val albumId: Int,
    val date: Int,
    @SerializedName("has_tags")
    val hasTags: Boolean,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val sizes: List<SizeXX>,
    val text: String,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable