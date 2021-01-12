package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answer(
    val id: Int,
    val rate: Double,
    val text: String,
    val votes: Int
) : Parcelable