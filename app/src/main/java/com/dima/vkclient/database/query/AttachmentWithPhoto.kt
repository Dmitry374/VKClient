package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.AttachmentEntity
import com.dima.vkclient.database.entity.PhotoSizeEntity

data class AttachmentWithPhoto(
    @Embedded
    val attachment: AttachmentEntity,
    @Relation(parentColumn = "id", entityColumn = "id_attachment_photo")
    val photos: List<PhotoSizeEntity>?
)