package com.dima.vkclient.data.domain.userprofile

class CareerDomain(
    val company: String,
    val userProfileId: Int,
    val cityId: Int,
    val cityName: String,
    val countryId: Int,
    val countryName: String,
    val position: String,
    val from: Int,
    val until: Int
)