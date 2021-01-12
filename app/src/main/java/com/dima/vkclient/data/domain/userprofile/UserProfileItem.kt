package com.dima.vkclient.data.domain.userprofile

data class UserProfileItem(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val bdate: String,
    val about: String,
    val city: String,
    val country: String,
    val career: List<CareerDomain>?,
    val domain: String,
    val educationForm: String?,
    val educationStatus: String?,
    val faculty: Int,
    val facultyName: String,
    val followersCount: Int,
    val graduation: Int,
    val isClosed: Boolean,
    val lastSeen: LastSeenDomain,
    val online: Int,
    val onlineMobile: Int,
    val photo50: String,
    val photo100: String,
    val sex: Int,
    val university: Int,
    val universityName: String,
    val canAccessClosed: Boolean
)