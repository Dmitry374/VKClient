package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainArtist(
    val domain: String,
    val id: String,
    val name: String
) : Parcelable