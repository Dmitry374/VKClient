package com.dima.vkclient.data.net.wall

import com.google.gson.annotations.SerializedName

data class WallDonutNet(
    @SerializedName("is_donut")
    val isDonut: Boolean
)