package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dima.vkclient.database.local.LikesLocalComment

@Entity(tableName = "comment")
data class CommentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "from_id")
    val fromId: Int,
    @ColumnInfo(name = "from_id_abs")
    val fromIdAbs: Int,
    @ColumnInfo(name = "post_id")
    val postId: Int,
    @ColumnInfo(name = "owner_id")
    val ownerId: Int,
    val date: Long,
    val text: String?,
    @Embedded
    val likes: LikesLocalComment?
)