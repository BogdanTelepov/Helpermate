package com.neobis.models.food


import com.google.gson.annotations.SerializedName

data class AddFoodModelRequest(
    @SerializedName("calories")
    val calories: Double?,
    @SerializedName("carbohydrates")
    val carbohydrates: Double?,
    @SerializedName("fats")
    val fats: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("proteins")
    val proteins: Double?
)