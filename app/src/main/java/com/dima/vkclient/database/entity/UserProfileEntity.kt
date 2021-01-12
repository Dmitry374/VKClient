package com.dima.vkclient.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dima.vkclient.database.local.CityLocal
import com.dima.vkclient.database.local.CountryLocal
import com.dima.vkclient.database.local.LastSeenLocal

@Entity(tableName = "user_profile")
class UserProfileEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val bdate: String,
    val about: String,
    @Embedded
    val city: CityLocal,
    @Embedded
    val country: CountryLocal,
    val domain: String,
    @ColumnInfo(name = "education_form")
    val educationForm: String?,
    @ColumnInfo(name = "education_status")
    val educationStatus: String?,
    val faculty: Int,
    @ColumnInfo(name = "faculty_name")
    val facultyName: String,
    @ColumnInfo(name = "followers_count")
    val followersCount: Int,
    val graduation: Int,
    @ColumnInfo(name = "is_closed")
    val isClosed: Boolean,
    @Embedded
    val lastSeen: LastSeenLocal,
    val online: Int,
    @ColumnInfo(name = "online_mobile")
    val onlineMobile: Int,
    @ColumnInfo(name = "photo_50")
    val photo50: String,
    @ColumnInfo(name = "photo_100")
    val photo100: String,
    val sex: Int,
    val university: Int,
    @ColumnInfo(name = "university_name")
    val universityName: String,
    @ColumnInfo(name = "can_access_closed")
    val canAccessClosed: Boolean
)