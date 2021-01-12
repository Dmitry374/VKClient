package com.dima.vkclient.data.domain.comment

import com.dima.vkclient.data.domain.post.GroupDomain
import com.dima.vkclient.data.domain.post.ProfileDomain

class CommentItem(
    val comment: CommentDomain,
    val group: GroupDomain?,
    val profile: ProfileDomain?
)