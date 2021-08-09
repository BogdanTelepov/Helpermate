package com.neobis.models.auth

import com.google.gson.annotations.SerializedName

data class RestorePasswordStepTwoResponse(

    @SerializedName("jwt")
    val token: String,
    val message: String
)
