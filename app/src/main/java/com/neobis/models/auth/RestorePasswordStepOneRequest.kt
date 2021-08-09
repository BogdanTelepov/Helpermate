package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RestorePasswordStepOneRequest(
    @SerializedName("email")
    val email: String
)
