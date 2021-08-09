package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RestorePasswordStepTwoRequest(
    @SerializedName("confirmCode")
    val confirmCode: String,
    @SerializedName("email")
    val email: String
)
