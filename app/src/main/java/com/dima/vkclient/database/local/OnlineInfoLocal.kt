package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class OnlineInfoLocal(
    @ColumnInfo(name = "app_id")
    val appId: Int,
    @ColumnInfo(name = "is_mobile")
    val isMobile: Boolean,
    @ColumnInfo(name = "is_online")
    val isOnline: Boolean,
    @ColumnInfo(name = "last_seen")
    val lastSeen: Int,
    val visible: Boolean
)