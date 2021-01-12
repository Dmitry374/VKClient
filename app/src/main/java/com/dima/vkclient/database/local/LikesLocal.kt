package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class LikesLocal(
    @ColumnInfo(name = "can_like")
    val canLike: Int,
    @ColumnInfo(name = "can_publish")
    val canPublish: Int,
    @ColumnInfo(name = "count_likes")
    var count: Int,
    @ColumnInfo(name = "user_likes")
    var userLikes: Int
)