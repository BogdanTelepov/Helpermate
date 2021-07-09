package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class UserActivateResponse(
    @SerializedName("Fail")
    val error: String
)
