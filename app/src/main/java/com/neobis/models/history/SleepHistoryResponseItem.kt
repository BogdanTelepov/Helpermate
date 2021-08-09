package com.neobis.models.history


import com.google.gson.annotations.SerializedName

data class SleepHistoryResponseItem(
    @SerializedName("createdDate")
    val createdDate: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("startTime")
    val startTime: String?
)