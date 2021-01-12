package com.dima.vkclient.data.net.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poll(
    val anonymous: Boolean,
    @SerializedName("answer_ids")
    val answerIds: List<Int>,
    val answers: List<Answer>,
    @SerializedName("author_id")
    val authorId: Int,
    @SerializedName("can_edit")
    val canEdit: Boolean,
    @SerializedName("can_report")
    val canReport: Boolean,
    @SerializedName("can_share")
    val canShare: Boolean,
    @SerializedName("can_vote")
    val canVote: Boolean,
    val closed: Boolean,
    val created: Int,
    @SerializedName("disable_unvote")
    val disableUnvote: Boolean,
    @SerializedName("end_date")
    val endDate: Int,
    val friends: List<Friend>,
    val id: Int,
    @SerializedName("is_board")
    val isBoard: Boolean,
    val multiple: Boolean,
    @SerializedName("owner_id")
    val ownerId: Int,
    val photo: PhotoXX,
    val question: String,
    val votes: Int
) : Parcelable