package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class UserSugarResponse(
    @SerializedName("createdDate")
    val createdDate: String?,
    @SerializedName("isNormal")
    val isNormal: Boolean?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("value")
    val value: Double?
)