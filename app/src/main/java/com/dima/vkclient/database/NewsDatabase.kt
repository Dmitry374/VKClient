package com.dima.vkclient.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dima.vkclient.database.entity.*

@Database(
    entities = [PostEntity::class, AttachmentEntity::class, PhotoSizeEntity::class, GroupEntity::class, CommentEntity::class, ProfileEntity::class, UserProfileEntity::class, CareerEntity::class, WallPostEntity::class, AttachmentWallEntity::class, PhotoSizeWallEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}