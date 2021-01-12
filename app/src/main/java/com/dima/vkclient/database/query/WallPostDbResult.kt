package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.AttachmentWallEntity
import com.dima.vkclient.database.entity.GroupEntity
import com.dima.vkclient.database.entity.ProfileEntity
import com.dima.vkclient.database.entity.WallPostEntity

class WallPostDbResult(
    @Embedded
    val post: WallPostEntity,
    @Relation(parentColumn = "owner_id_abs", entityColumn = "id")
    val group: GroupEntity?,
    @Relation(parentColumn = "owner_id_abs", entityColumn = "id")
    val profile: ProfileEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "wall_post_id",
        entity = AttachmentWallEntity::class
    )
    val attachments: List<WallAttachmentWithPhoto>?
)