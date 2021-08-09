package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class UserSleepResponse(
    @SerializedName("createdDate")
    val createdDate: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("startTime")
    val startTime: String?
)