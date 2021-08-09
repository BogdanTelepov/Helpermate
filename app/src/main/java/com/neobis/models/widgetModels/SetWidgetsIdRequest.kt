package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class SetWidgetsIdRequest(
    @SerializedName("widgetIds")
    val widgetIds: List<Int>?
)