package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class Sleep(
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("trackedDate")
    val trackedDate: String?
)