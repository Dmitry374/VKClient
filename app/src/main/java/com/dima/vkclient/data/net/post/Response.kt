package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(
    val groups: List<Group>,
    val items: List<Item>,
    @SerializedName("next_from")
    val nextFrom: String,
    val profiles: List<Profile>
) : Parcelable