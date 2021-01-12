package com.dima.vkclient.data.net.wall

import com.dima.vkclient.data.net.post.Group
import com.dima.vkclient.data.net.post.Profile

data class WallResponseNet(
    val count: Int,
    val groups: List<Group>,
    val items: List<WallItemNet>,
    val profiles: List<Profile>
)