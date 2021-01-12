package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Market(
    val availability: Int,
    val category: Category,
    val description: String,
    val id: Int,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("owner_id")
    val ownerId: Int,
    val price: Price,
    @SerializedName("thumb_photo")
    val thumbPhoto: String,
    val title: String
) : Parcelable