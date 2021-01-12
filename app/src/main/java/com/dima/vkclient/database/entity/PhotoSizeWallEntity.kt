package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo_size_wall",
    foreignKeys = [ForeignKey(
        entity = AttachmentWallEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_attachment_photo"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PhotoSizeWallEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "id_attachment_photo", index = true)
    val idAttachmentPhoto: Int,
    val height: Int,
    val type: String,
    val url: String,
    val width: Int
)