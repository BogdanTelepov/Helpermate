package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class UserActivateRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("code")
    val code: Int
)
