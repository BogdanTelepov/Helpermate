package com.neobis.models.widgetModels


import com.google.gson.annotations.SerializedName

data class MedicationsRequest(
    @SerializedName("names")
    val names: List<String>
)