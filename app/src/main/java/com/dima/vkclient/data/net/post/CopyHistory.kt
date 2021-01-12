package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CopyHistory(
    val attachments: List<AttachmentX>,
    val date: Int,
    @SerializedName("from_id")
    val fromId: Int,
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("post_source")
    val postSource: PostSource,
    @SerializedName("post_type")
    val postType: String,
    val text: String
) : Parcelable