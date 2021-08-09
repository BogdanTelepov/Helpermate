package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RegisterStepTwoResponse(
    @SerializedName("jwt")
    val jwtToken: String
)
