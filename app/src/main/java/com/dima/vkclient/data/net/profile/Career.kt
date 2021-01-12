package com.dima.vkclient.data.net.profile

import com.google.gson.annotations.SerializedName

data class Career(
    @SerializedName("city_id")
    val cityId: Int,
    val company: String,
    @SerializedName("country_id")
    val countryId: Int,
    val from: Int,
    val position: String,
    val until: Int
)