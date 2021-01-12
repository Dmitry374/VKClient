package com.dima.vkclient.data.net.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    val about: String,
    val bdate: String,
    @SerializedName("can_access_closed")
    val canAccessClosed: Boolean,
    val career: List<Career>,
    val city: City,
    val country: Country,
    val domain: String,
    @SerializedName("education_form")
    val educationForm: String,
    @SerializedName("education_status")
    val educationStatus: String,
    val faculty: Int,
    @SerializedName("faculty_name")
    val facultyName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("followers_count")
    val followersCount: Int,
    val graduation: Int,
    val id: Int,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("last_seen")
    val lastSeen: LastSeen,
    val online: Int,
    @SerializedName("online_mobile")
    val onlineMobile: Int,
    @SerializedName("photo_100")
    val photo100: String,
    @SerializedName("photo_50")
    val photo50: String,
    val sex: Int,
    val university: Int,
    @SerializedName("university_name")
    val universityName: String
)