package com.neobis.repository

import com.neobis.models.auth.*
import com.neobis.network.AuthorizationApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val authorizationApi: AuthorizationApi
) {


    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authorizationApi.register(registerRequest)
    }

    suspend fun activationAccount(userActivateRequest: UserActivateRequest): Response<UserActivateResponse> {
        return authorizationApi.activationAccount(userActivateRequest)
    }

    suspend fun userLogin(userLoginRequest: UserLoginRequest): Response<UserLoginResponse> {
        return authorizationApi.userLogin(userLoginRequest)
    }


}