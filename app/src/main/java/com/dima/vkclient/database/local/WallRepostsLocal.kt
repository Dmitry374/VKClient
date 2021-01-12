package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class WallRepostsLocal(
    @ColumnInfo(name = "count_reposts")
    val count: Int,
    @ColumnInfo(name = "mail_count")
    val mailCount: Int,
    @ColumnInfo(name = "user_reposted")
    val userReposted: Int,
    @ColumnInfo(name = "wall_count")
    val wallCount: Int
)