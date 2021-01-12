package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val height: Int,
    val url: String,
    val width: Int,
    @SerializedName("with_padding")
    val withPadding: Int
) : Parcelable