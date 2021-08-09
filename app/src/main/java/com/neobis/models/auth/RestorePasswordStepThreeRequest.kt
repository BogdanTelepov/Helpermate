package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RestorePasswordStepThreeRequest(
    @SerializedName("confirmPassword")
    val confirmPassword: String,
    @SerializedName("password")
    val password: String
)
