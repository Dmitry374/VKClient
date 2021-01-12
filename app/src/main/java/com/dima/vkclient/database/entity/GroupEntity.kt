package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "is_closed")
    val isClosed: Int,
    val name: String,
    @ColumnInfo(name = "photo_100")
    val photo100: String,
    @ColumnInfo(name = "photo_200")
    val photo200: String,
    @ColumnInfo(name = "photo_50")
    val photo50: String,
    @ColumnInfo(name = "screen_name")
    val screenName: String?,
    val type: String?
)