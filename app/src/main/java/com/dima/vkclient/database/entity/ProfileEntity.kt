package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dima.vkclient.database.local.OnlineInfoLocal

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val online: Int,
    @ColumnInfo(name = "photo_100")
    val photo100: String,
    @ColumnInfo(name = "photo_50")
    val photo50: String,
    @ColumnInfo(name = "screen_name")
    val screenName: String?,
    val sex: Int,
    @Embedded
    val onlineInfo: OnlineInfoLocal
)