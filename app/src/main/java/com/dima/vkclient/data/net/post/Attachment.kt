package com.dima.vkclient.data.net.post

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attachment(
    val album: Album?,
    val audio: Audio?,
    val doc: Doc?,
    val link: Link?,
    val market: Market?,
    val poll: Poll?,
    val photo: PhotoXX?,
    val podcast: Podcast?,
    val type: String?,
    val video: Video?
) : Parcelable