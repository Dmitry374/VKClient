package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class CityLocal(
    @ColumnInfo(name = "city_id")
    val id: Int,
    @ColumnInfo(name = "city_name")
    val title: String
)