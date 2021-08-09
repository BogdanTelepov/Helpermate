package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class MedicationsResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("userId")
    val userId: Int
)