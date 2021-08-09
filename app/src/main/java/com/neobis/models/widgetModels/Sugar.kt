package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class Sugar(
    @SerializedName("trackedDate")
    val trackedDate: String?,
    @SerializedName("value")
    val value: Double?
)