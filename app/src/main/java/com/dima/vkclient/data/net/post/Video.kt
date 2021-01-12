package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    @SerializedName("access_key")
    val accessKey: String,
    @SerializedName("can_add")
    val canAdd: Int,
    @SerializedName("can_add_to_faves")
    val canAddToFaves: Int,
    @SerializedName("can_comment")
    val canComment: Int,
    @SerializedName("can_like")
    val canLike: Int,
    @SerializedName("can_repost")
    val canRepost: Int,
    @SerializedName("can_subscribe")
    val canSubscribe: Int,
    val comments: Int,
    val date: Long,
    val description: String,
    val duration: Int,
    @SerializedName("first_frame")
    val firstFrame: List<FirstFrame>,
    val height: Int,
    val id: Int,
    val image: List<Image>,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("owner_id")
    val ownerId: Int,
    val title: String,
    @SerializedName("track_code")
    val trackCode: String,
    val type: String,
    val views: Int,
    val width: Int
) : Parcelable