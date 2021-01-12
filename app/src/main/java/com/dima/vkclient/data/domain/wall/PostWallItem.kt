package com.dima.vkclient.data.domain.wall

import com.dima.vkclient.data.domain.post.AttachmentWithPhotoDomain
import com.dima.vkclient.data.domain.post.GroupDomain
import com.dima.vkclient.data.domain.post.ProfileDomain

class PostWallItem(
    val post: WallPostDomain,
    val group: GroupDomain?,
    val profile: ProfileDomain?,
    val attachments: List<AttachmentWithPhotoDomain>?
)