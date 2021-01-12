package com.dima.vkclient.data.net.wall

import com.google.gson.annotations.SerializedName

data class WallRepostsNet(
    val count: Int,
    @SerializedName("mail_count")
    val mailCount: Int,
    @SerializedName("user_reposted")
    val userReposted: Int,
    @SerializedName("wall_count")
    val wallCount: Int
)