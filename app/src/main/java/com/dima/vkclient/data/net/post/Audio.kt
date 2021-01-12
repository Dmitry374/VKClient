package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Audio(
    @SerializedName("album_id")
    val albumId: Int,
    val artist: String,
    val date: Long,
    val duration: Int,
    @SerializedName("genre_id")
    val genreId: Int,
    val id: Int,
    @SerializedName("is_explicit")
    val isExplicit: Boolean,
    @SerializedName("is_focus_track")
    val isFocusTrack: Boolean,
    @SerializedName("main_artists")
    val mainArtists: List<MainArtist>,
    @SerializedName("no_search")
    val noSearch: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("short_videos_allowed")
    val shortVideosAllowed: Boolean,
    @SerializedName("stories_allowed")
    val storiesAllowed: Boolean,
    @SerializedName("stories_cover_allowed")
    val storiesCoverAllowed: Boolean,
    val title: String,
    @SerializedName("track_code")
    val trackCode: String,
    val url: String
) : Parcelable