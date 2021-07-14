package com.neobis.network

import com.neobis.models.auth.*
import com.neobis.utils.Constants.LOGIN
import com.neobis.utils.Constants.REGISTRATION_STEP_1
import com.neobis.utils.Constants.REGISTRATION_STEP_2
import com.neobis.utils.Constants.REGISTRATION_STEP_3
import okhttp3.ResponseBody


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthorizationApi {

    @POST(REGISTRATION_STEP_1)
    suspend fun registerStepOne(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST(REGISTRATION_STEP_2)
    suspend fun registerStepTwo(@Body userActivateRequest: UserActivateRequest): Response<UserActivateResponse>

    @POST(REGISTRATION_STEP_3)
    suspend fun registerStepThree(@Body registerStep3Request: RegistrationStep3Request): Response<ResponseBody>


    @POST(LOGIN)
    suspend fun userLogin(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>
}
