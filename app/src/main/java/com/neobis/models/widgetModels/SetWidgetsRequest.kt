package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class SetWidgetsRequest(
    @SerializedName("userPressure")
    val userPressure: UserPressure,
    @SerializedName("userSleep")
    val userSleep: UserSleep,
    @SerializedName("userSugar")
    val userSugar: UserSugar,
    @SerializedName("widgetIds")
    val widgetIds: List<Int>
)