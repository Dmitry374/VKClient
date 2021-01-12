package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.AttachmentWallEntity
import com.dima.vkclient.database.entity.PhotoSizeWallEntity

class WallAttachmentWithPhoto(
    @Embedded
    val attachment: AttachmentWallEntity,
    @Relation(parentColumn = "id", entityColumn = "id_attachment_photo")
    val photos: List<PhotoSizeWallEntity>?
)