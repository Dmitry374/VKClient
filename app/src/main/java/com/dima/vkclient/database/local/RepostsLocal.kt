package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class RepostsLocal(
    @ColumnInfo(name = "count_reposts")
    val count: Int,
    @ColumnInfo(name = "user_reposted")
    val userReposted: Int
)