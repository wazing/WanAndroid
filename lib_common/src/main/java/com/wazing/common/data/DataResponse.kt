package com.wazing.common.data

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("errorCode") val code: Int,
    @SerializedName("errorMsg") val msg: String = "",
    @SerializedName("data") val data: T
)