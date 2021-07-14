package com.neobis.network

import com.neobis.models.auth.*
import com.neobis.utils.Constants.REGISTRATION_STEP_1


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthorizationApi {

    @POST(REGISTRATION_STEP_1)
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("/auth/activation")
    suspend fun activationAccount(@Body userActivateRequest: UserActivateRequest): Response<UserActivateResponse>

    @POST("/auth/token")
    suspend fun userLogin(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>
}
