package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterStepTwoRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("confirmCode")
    val confirmCode: String
)
