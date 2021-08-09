package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class SugarRequest(
    @SerializedName("value")
    val value: Int
)