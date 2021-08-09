package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserSleep(
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("startTime")
    val startTime: String
) :Serializable