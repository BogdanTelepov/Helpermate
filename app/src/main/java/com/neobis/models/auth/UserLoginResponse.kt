package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("detail")
    val error: String
)
