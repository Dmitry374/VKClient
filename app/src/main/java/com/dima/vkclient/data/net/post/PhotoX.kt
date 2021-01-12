package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoX(
    @SerializedName("album_id")
    val albumId: Int,
    val date: Long,
    @SerializedName("has_tags")
    val hasTags: Boolean,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val sizes: List<SizeX>,
    val text: String,
    @SerializedName("user_id")
    val userId: Int
) : Parcelable