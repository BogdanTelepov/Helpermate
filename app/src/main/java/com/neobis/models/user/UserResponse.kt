package com.neobis.models.user


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("diabetesStatus")
    val diabetesStatus: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Int
)