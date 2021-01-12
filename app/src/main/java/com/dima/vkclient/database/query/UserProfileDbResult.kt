package com.dima.vkclient.database.query

import androidx.room.Embedded
import androidx.room.Relation
import com.dima.vkclient.database.entity.CareerEntity
import com.dima.vkclient.database.entity.UserProfileEntity

class UserProfileDbResult(
    @Embedded
    val profile: UserProfileEntity,
    @Relation(parentColumn = "id", entityColumn = "user_profile_id")
    val career: List<CareerEntity>?
)