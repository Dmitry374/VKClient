package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class PhotoLocal(
    @ColumnInfo(name = "access_key")
    val accessKey: String?,
    val date: Long,
    @ColumnInfo(name = "has_tags")
    val hasTags: Boolean,
    @ColumnInfo(name = "photo_id")
    val photoId: Int,
    val text: String,
    @ColumnInfo(name = "user_id")
    val userId: Int
)