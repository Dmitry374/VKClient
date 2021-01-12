package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.AttachmentEntity
import com.dima.vkclient.database.entity.GroupEntity
import com.dima.vkclient.database.entity.PostEntity
import com.dima.vkclient.database.entity.ProfileEntity

data class PostWithGroupAndAttachment(
    @Embedded
    val post: PostEntity,
    @Relation(parentColumn = "source_id_abs", entityColumn = "id")
    val group: GroupEntity?,
    @Relation(parentColumn = "source_id_abs", entityColumn = "id")
    val profile: ProfileEntity?,
    @Relation(parentColumn = "id", entityColumn = "post_id", entity = AttachmentEntity::class)
    val attachments: List<AttachmentWithPhoto>?
)