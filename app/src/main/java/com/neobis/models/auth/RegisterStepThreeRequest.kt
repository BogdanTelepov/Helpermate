package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterStepThreeRequest(
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("diabetesStatusId")
    val diabetesStatusId: Int,
    @SerializedName("genderId")
    val genderId: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Int

)

