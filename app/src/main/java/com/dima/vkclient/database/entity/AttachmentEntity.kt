package com.dima.vkclient.database.entity

import androidx.room.*
import com.dima.vkclient.database.local.PhotoLocal

@Entity(
    tableName = "attachment",
    foreignKeys = [ForeignKey(
        entity = PostEntity::class,
        parentColumns = ["id"],
        childColumns = ["post_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AttachmentEntity(
    @PrimaryKey
    val id: Int,
    val type: String?,
    @Embedded
    val photo: PhotoLocal?,
    @ColumnInfo(name = "post_id", index = true)
    val postId: Int
)