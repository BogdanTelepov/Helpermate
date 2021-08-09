package com.neobis.models.widgetModels

import com.google.gson.annotations.SerializedName

data class MainPageGetSleepValues(

    @SerializedName("sleep")
    val sleep: Sleep?
)
