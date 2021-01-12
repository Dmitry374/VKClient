package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dima.vkclient.database.local.CommentsLocal
import com.dima.vkclient.database.local.LikesLocal
import com.dima.vkclient.database.local.RepostsLocal
import com.dima.vkclient.database.local.ViewsLocal

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @Embedded
    val comments: CommentsLocal,
    val date: Long,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @Embedded
    val likes: LikesLocal,
    @ColumnInfo(name = "marked_as_ads")
    val markedAsAds: Int,
    @ColumnInfo(name = "post_type")
    val postType: String,
    @Embedded
    val reposts: RepostsLocal,
    @ColumnInfo(name = "source_id")
    val sourceId: Int,
    @ColumnInfo(name = "source_id_abs")
    val sourceIdAbs: Int,
    val text: String,
    val type: String,
    @Embedded
    val views: ViewsLocal?
)