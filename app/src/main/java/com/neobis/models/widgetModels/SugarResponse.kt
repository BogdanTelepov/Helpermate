package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class SugarResponse(
    @SerializedName("message")
    val message: String
)