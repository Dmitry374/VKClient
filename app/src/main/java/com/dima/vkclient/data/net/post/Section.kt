package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Section(
    val id: Int,
    val name: String
) : Parcelable