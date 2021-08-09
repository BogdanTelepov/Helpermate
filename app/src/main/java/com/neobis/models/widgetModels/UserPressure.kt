package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserPressure(
    @SerializedName("diastolic")
    val diastolic: Int,
    @SerializedName("systolic")
    val systolic: Int
) : Serializable