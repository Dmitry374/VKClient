package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Podcast(
    val artist: String,
    val date: Int,
    val duration: Int,
    val id: Int,
    @SerializedName("is_explicit")
    val isExplicit: Boolean,
    @SerializedName("is_focus_track")
    val isFocusTrack: Boolean,
    @SerializedName("lyrics_id")
    val lyricsId: Int,
    @SerializedName("no_search")
    val noSearch: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("podcast_info")
    val podcastInfo: PodcastInfo,
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