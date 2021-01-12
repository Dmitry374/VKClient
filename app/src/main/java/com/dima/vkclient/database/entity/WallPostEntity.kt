package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dima.vkclient.database.local.CommentsLocal
import com.dima.vkclient.database.local.LikesLocal
import com.dima.vkclient.database.local.ViewsLocal
import com.dima.vkclient.database.local.WallRepostsLocal

@Entity(tableName = "wall_post")
class WallPostEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "from_id")
    val fromId: Int,
    @ColumnInfo(name = "owner_id")
    val ownerId: Int,
    @ColumnInfo(name = "owner_id_abs")
    val ownerIdAbs: Int,
    val date: Long,
    @ColumnInfo(name = "post_type")
    val postType: String,
    val text: String,
    @ColumnInfo(name = "can_edit")
    val canEdit: Int,
    @ColumnInfo(name = "can_delete")
    val canDelete: Int,
    @ColumnInfo(name = "can_pin")
    val canPin: Int,
    @ColumnInfo(name = "can_archive")
    val canArchive: Boolean,
    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean,
    @Embedded
    val comments: CommentsLocal,
    @Embedded
    val likes: LikesLocal,
    @Embedded
    val reposts: WallRepostsLocal,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "short_text_rate")
    val shortTextRate: Double,
    @Embedded
    val views: ViewsLocal?
)