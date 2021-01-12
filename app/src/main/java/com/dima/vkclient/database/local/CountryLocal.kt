package com.dima.vkclient.database.local

import androidx.room.ColumnInfo

class CountryLocal(
    @ColumnInfo(name = "country_id")
    val id: Int,
    @ColumnInfo(name = "country_name")
    val title: String
)