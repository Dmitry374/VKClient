package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    val created: Int,
    val description: String,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val size: Int,
    val thumb: Thumb,
    val title: String,
    val updated: Int
) : Parcelable