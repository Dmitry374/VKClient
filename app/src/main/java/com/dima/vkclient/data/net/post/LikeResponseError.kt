package com.dima.vkclient.data.net.post

import com.google.gson.annotations.SerializedName

class LikeResponseError(
    val error: Error
)

data class Error(
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("error_msg")
    val errorMsg: String,
    @SerializedName("request_params")
    val requestParams: List<RequestParam>
)

data class RequestParam(
    val key: String,
    val value: String
)