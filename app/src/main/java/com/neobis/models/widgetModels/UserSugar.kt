package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserSugar(
    @SerializedName("time")
    val time: String,
    @SerializedName("value")
    val value: Int
) : Serializable