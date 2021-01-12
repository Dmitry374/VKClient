package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class LastSeenLocal(
    @ColumnInfo(name = "last_seen_platform")
    val platform: Int,
    @ColumnInfo(name = "last_seen_time")
    val time: Long
)