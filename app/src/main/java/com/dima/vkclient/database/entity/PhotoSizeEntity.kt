package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo_size",
    foreignKeys = [ForeignKey(
        entity = AttachmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_attachment_photo"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PhotoSizeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "id_attachment_photo", index = true)
    val idAttachmentPhoto: Int,
    val height: Int,
    val type: String,
    val url: String,
    val width: Int
)