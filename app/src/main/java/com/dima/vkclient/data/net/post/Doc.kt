package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Doc(
    @SerializedName("access_key")
    val accessKey: String,
    val date: Long,
    val ext: String,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val preview: Preview,
    val size: Int,
    val title: String,
    val type: Int,
    val url: String
) : Parcelable