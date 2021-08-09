package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterStepOneRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("confirmPassword")
    val confirmPassword: String
)
