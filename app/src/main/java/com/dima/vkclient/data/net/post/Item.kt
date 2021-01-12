package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val attachments: List<Attachment>?,
    @SerializedName("can_doubt_category")
    val canDoubtCategory: Boolean,
    @SerializedName("can_set_category")
    val canSetCategory: Boolean,
    @SerializedName("category_action")
    val categoryAction: CategoryAction,
    val comments: Comments,
    @SerializedName("copy_history")
    val copyHistory: List<CopyHistory>,
    val copyright: Copyright,
    val date: Long,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    val likes: Likes,
    @SerializedName("marked_as_ads")
    val markedAsAds: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("post_source")
    val postSource: PostSource,
    @SerializedName("post_type")
    val postType: String,
    val reposts: Reposts,
    @SerializedName("signer_id")
    val signerId: Int,
    @SerializedName("source_id")
    val sourceId: Int,
    val text: String,
    @SerializedName("topic_id")
    val topicId: Int,
    val type: String,
    val views: Views?
) : Parcelable