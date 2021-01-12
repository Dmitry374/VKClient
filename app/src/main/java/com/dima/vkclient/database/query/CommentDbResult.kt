package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.CommentEntity
import com.dima.vkclient.database.entity.GroupEntity
import com.dima.vkclient.database.entity.ProfileEntity

class CommentDbResult(
    @Embedded
    val comment: CommentEntity,
    @Relation(parentColumn = "from_id_abs", entityColumn = "id")
    val group: GroupEntity?,
    @Relation(parentColumn = "from_id", entityColumn = "id")
    val profile: ProfileEntity?
)