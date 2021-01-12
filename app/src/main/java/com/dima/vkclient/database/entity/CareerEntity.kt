package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "career",
    primaryKeys = ["company", "from"],
    foreignKeys = [ForeignKey(
        entity = UserProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_profile_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class CareerEntity(
    val company: String,
    @ColumnInfo(name = "user_profile_id", index = true)
    val userProfileId: Int,
    @ColumnInfo(name = "city_id")
    val cityId: Int,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "country_id")
    val countryId: Int,
    @ColumnInfo(name = "country_name")
    val countryName: String,
    val position: String,
    val from: Int,
    val until: Int
)