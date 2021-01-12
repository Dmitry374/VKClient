package com.dima.vkclient.data.net.wall

import com.dima.vkclient.data.net.post.*
import com.google.gson.annotations.SerializedName

data class WallItemNet(
    val attachments: List<Attachment>?,
    @SerializedName("can_archive")
    val canArchive: Boolean,
    @SerializedName("can_edit")
    val canEdit: Int,
    @SerializedName("can_delete")
    val canDelete: Int,
    @SerializedName("can_pin")
    val canPin: Int,
    val comments: Comments,
    @SerializedName("copy_history")
    val copyHistory: List<CopyHistory>,
    val date: Long,
    val donut: WallDonutNet,
    @SerializedName("from_id")
    val fromId: Int,
    val id: Int,
    @SerializedName("is_archived")
    val isArchived: Boolean,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val likes: Likes,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("post_source")
    val postSource: WallPostSourceNet,
    @SerializedName("post_type")
    val postType: String,
    val reposts: WallRepostsNet,
    @SerializedName("short_text_rate")
    val shortTextRate: Double,
    val text: String,
    val views: Views?
)