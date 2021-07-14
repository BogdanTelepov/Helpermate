package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class UserActivateResponse(
    @SerializedName("jwt")
    val jwtToken: String
)
