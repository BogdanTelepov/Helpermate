package com.neobis.repository

import com.neobis.models.auth.*
import com.neobis.network.AuthorizationApi
import retrofit2.Response
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val authorizationApi: AuthorizationApi
) {


    suspend fun registrationStepOne(registerStepOneRequest: RegisterStepOneRequest): Response<RegisterStepOneResponse> {
        return authorizationApi.registerStepOne(registerStepOneRequest)
    }

    suspend fun registrationStepTwo(registerStepTwoRequest: RegisterStepTwoRequest): Response<RegisterStepTwoResponse> {
        return authorizationApi.registerStepTwo(registerStepTwoRequest)
    }

    suspend fun registrationStepThree(registerStepThreeRequest: RegisterStepThreeRequest): Response<RegisterStepThreeResponse> {
        return authorizationApi.registerStepThree(registerStepThreeRequest)
    }

    suspend fun userLogin(userLoginRequest: UserLoginRequest): Response<UserLoginResponse> {
        return authorizationApi.userLogin(userLoginRequest)
    }

    suspend fun restorePasswordStepOne(restorePasswordStepOneRequest: RestorePasswordStepOneRequest): Response<RestorePasswordStepOneResponse> {
        return authorizationApi.restorePasswordStepOne(restorePasswordStepOneRequest)
    }

    suspend fun restorePasswordStepTwo(restorePasswordStepTwoRequest: RestorePasswordStepTwoRequest): Response<RestorePasswordStepTwoResponse> {
        return authorizationApi.restorePasswordStepTwo(restorePasswordStepTwoRequest)
    }

    suspend fun restorePasswordStepThree(restorePasswordStepThreeRequest: RestorePasswordStepThreeRequest): Response<RestorePasswordStepThreeResponse> {
        return authorizationApi.restorePasswordStepThree(restorePasswordStepThreeRequest)
    }


}