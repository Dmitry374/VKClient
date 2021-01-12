package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class ViewsLocal(
    @ColumnInfo(name = "count_views")
    val count: Int
)