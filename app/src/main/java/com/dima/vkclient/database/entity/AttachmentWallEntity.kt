package com.dima.vkclient.database.entity

import androidx.room.*
import com.dima.vkclient.database.local.PhotoLocal

@Entity(
    tableName = "attachment_wall",
    foreignKeys = [ForeignKey(
        entity = WallPostEntity::class,
        parentColumns = ["id"],
        childColumns = ["wall_post_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AttachmentWallEntity(
    @PrimaryKey
    val id: Int,
    val type: String?,
    @Embedded
    val photo: PhotoLocal?,
    @ColumnInfo(name = "wall_post_id", index = true)
    val postId: Int
)