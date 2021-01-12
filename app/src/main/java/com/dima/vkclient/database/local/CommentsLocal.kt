package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class CommentsLocal(
    @ColumnInfo(name = "can_post")
    val canPost: Int,
    @ColumnInfo(name = "count_comments")
    val count: Int,
    @ColumnInfo(name = "groups_can_post")
    val groupsCanPost: Boolean
)